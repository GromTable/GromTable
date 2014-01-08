package com.gromtable.server.api.getdocumentinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gromtable.server.api.helper.VoteFilter;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.Pair;
import com.gromtable.server.core.data.RowKey;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityUserAndVote;
import com.gromtable.server.core.entity.VoteDocumentDecision;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.hashout.HashoutDelegateToUser;
import com.gromtable.server.core.hashout.HashoutDocumentToUser;
import com.gromtable.server.core.hashout.HashoutUserToDelegate;
import com.gromtable.server.core.loader.Loader;

public class GetDocumentInfoImpl extends Loader<GetDocumentInfoResult> {
  private Id documentId;
  private boolean showVotes;
  private long voteTime;

  public GetDocumentInfoImpl(Id documentId, boolean showVotes, long voteTime) {
    this.documentId = documentId;
    this.showVotes = showVotes;
    if (voteTime == 0) {
      this.voteTime = BaseEnvironment.getEnvironment().getTime().getNanoTime();
    } else {
      this.voteTime = voteTime;
    }
  }

  public GetDocumentInfoResult genLoad() {
    EntityDocument document = EntityDocument.load(documentId);
    EntityUser author = EntityUser.load(document.getCreatorId());
    GetDocumentInfoResult result = new GetDocumentInfoResult(document, author);
    long currentTime = BaseEnvironment.getEnvironment().getTime().getNanoTime();
    if (showVotes) {
      HashoutDocumentToUser hashoutDocumentToUser = new HashoutDocumentToUser();
      HashoutUserToDelegate hashoutUserToDelegate = new HashoutUserToDelegate();
      HashoutDelegateToUser hashoutDelegateToUser = new HashoutDelegateToUser();
      List<EntityUserAndVote> documentVotes = hashoutDocumentToUser.loadEntities(documentId);
      documentVotes = VoteFilter.filterVotes(documentVotes, voteTime);
      List<Key> votes = getKeys(documentVotes);
      Map<Key, List<EntityUserAndVote>> userVotes = hashoutUserToDelegate.loadMultiEntities(votes);
      Map<Key, List<EntityUserAndVote>> delegateVotes = hashoutDelegateToUser.loadMultiEntities(votes);
      userVotes = filterVotes(userVotes, voteTime);
      delegateVotes = filterVotes(delegateVotes, voteTime);
      setUpVotes(result, documentVotes, userVotes, delegateVotes);
    }
    if (document.isNeedDecision(currentTime)) {
      document.doDecision(currentTime);
    }
    return result;
  }

  private List<Key> getKeys(List<EntityUserAndVote> documentVotes) {
    List<Key> keys = new ArrayList<Key>();
    for (EntityUserAndVote userAndVote : documentVotes) {
      keys.add(userAndVote.getUser().getId());
    }
    return keys;
  }

  private void setUpVotes(
      GetDocumentInfoResult result,
      List<EntityUserAndVote> documentVotes,
      Map<Key, List<EntityUserAndVote>> userVotes,
      Map<Key, List<EntityUserAndVote>> delegateVotes) {
    Map<Key, EntityUserAndVote> documentUserToVote = createDocumentUserToVote(documentVotes);
    checkUserVotes(userVotes);
    Map<Key, DelegateGroupVotes> allVotesMap = new HashMap<Key, DelegateGroupVotes>();
    for (EntityUserAndVote documentVote : documentVotes) {
      Pair<EntityUserAndVote> delegateAndUser =
        createDelegateAndUser(documentVote, userVotes, delegateVotes, documentUserToVote);
      DelegateGroupVotes delegateGroupVotes =
        createDelegateGroupVotes(delegateAndUser.getFirst(), allVotesMap, delegateVotes);
      delegateGroupVotes.addUser(delegateAndUser.getSecond());
    }

    List<DelegateGroupVotes> allVotes = new ArrayList<DelegateGroupVotes>(allVotesMap.values());
    DecisionsMap totalVotes = calculateTotalVotes(allVotes);
    result.setVotes(totalVotes, allVotes);
  }

  private Map<Key, EntityUserAndVote> createDocumentUserToVote(List<EntityUserAndVote> documentVotes) {
    Map<Key, EntityUserAndVote> documentUserToVote = new HashMap<Key, EntityUserAndVote>();
    for (EntityUserAndVote documentVote : documentVotes) {
      Key key = documentVote.getUser().getId();
      EntityUserAndVote previousVote = documentUserToVote.get(key);
      if (previousVote != null) {
        throw new GetDocumentInfoException(
          "More then one vote: " + previousVote.getVote().getId() + " " + documentVote.getVote().getId());
      }
      documentUserToVote.put(key, documentVote);
    }
    return documentUserToVote;
  }

  private void checkUserVotes(Map<Key, List<EntityUserAndVote>> userVotes) {
    for (Map.Entry<Key, List<EntityUserAndVote>> userVote : userVotes.entrySet()) {
      if (userVote.getValue().size() != 1) {
        throw new GetDocumentInfoException("Should be one vote: " + userVote.getKey());
      }
    }
  }

  private Pair<EntityUserAndVote> createDelegateAndUser(
      EntityUserAndVote documentVote,
      Map<Key, List<EntityUserAndVote>> userVotes,
      Map<Key, List<EntityUserAndVote>> delegateVotes,
      Map<Key, EntityUserAndVote> documentUserToVote) {
    EntityUserAndVote delegate = null;
    EntityUserAndVote user = null;
    if (delegateVotes.containsKey(documentVote.getUser().getId())) {
      delegate = documentVote;
    } else {
      List<EntityUserAndVote> delegateList = userVotes.get(documentVote.getUser().getId());
      if (delegateList != null) {
        Key delegateKey = delegateList.get(0).getUser().getId();
        delegate = documentUserToVote.get(delegateKey);
      }
      user = documentVote;
    }
    return new Pair<EntityUserAndVote>(delegate, user);
  }

  private DelegateGroupVotes createDelegateGroupVotes(
      EntityUserAndVote delegate,
      Map<Key, DelegateGroupVotes> allVotesMap,
      Map<Key, List<EntityUserAndVote>> delegateVotes) {
    Key delegateKey = RowKey.EMPTY;
    if (delegate != null) {
      delegateKey = delegate.getUser().getId();
    }

    DelegateGroupVotes delegateGroupVotes = allVotesMap.get(delegateKey);
    if (delegateGroupVotes == null) {
      UserAndDecision delegateUserDecision = null;
      VoteDocumentDecision voteDocumentDecision = null;
      if (delegate != null) {
        voteDocumentDecision = delegate.getVote().getVoteDocumentDecision();
        delegateUserDecision = new UserAndDecision(delegate.getUser(), voteDocumentDecision);
      }
      delegateGroupVotes = new DelegateGroupVotes(delegateUserDecision);
      if (delegate != null) {
        List<EntityUserAndVote> votesForDelegate = delegateVotes.get(delegateKey);
        if (votesForDelegate != null) {
          delegateGroupVotes.getGroupVotes().add(new DecisionsMap(voteDocumentDecision, votesForDelegate.size() + 1));
        }
      }
      allVotesMap.put(delegateKey, delegateGroupVotes);
    }
    return delegateGroupVotes;
  }

  private DecisionsMap calculateTotalVotes(List<DelegateGroupVotes> allVotes) {
    DecisionsMap result = new DecisionsMap();
    for (DelegateGroupVotes delegateGroup : allVotes) {
      result.add(delegateGroup.getGroupVotes());
    }
    return result;
  }

  private Map<Key, List<EntityUserAndVote>> filterVotes(Map<Key, List<EntityUserAndVote>> votes, long voteTime) {
    Map<Key, List<EntityUserAndVote>> filteredVotes = new HashMap<Key, List<EntityUserAndVote>>();
    for (Map.Entry<Key, List<EntityUserAndVote>> vote : votes.entrySet()) {
      List<EntityUserAndVote> filteredVote = VoteFilter.filterVotes(vote.getValue(), voteTime);
      if (!filteredVote.isEmpty()) {
        filteredVotes.put(vote.getKey(), filteredVote);
      }
    }
    return filteredVotes;
  }
}
