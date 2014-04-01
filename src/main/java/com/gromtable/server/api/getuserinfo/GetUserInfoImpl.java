package com.gromtable.server.api.getuserinfo;

import java.util.ArrayList;
import java.util.List;

import com.gromtable.server.api.helper.VoteFilter;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityAction;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityUserAndVote;
import com.gromtable.server.core.entity.EntityVote;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.hashout.HashoutDelegateToUser;
import com.gromtable.server.core.hashout.HashoutUserToAction;
import com.gromtable.server.core.hashout.HashoutUserToDelegate;
import com.gromtable.server.core.loader.Loader;

public class GetUserInfoImpl extends Loader<GetUserInfoResult> {
  private Id userId;
  private boolean showVotes;
  private long votesTime;

  public GetUserInfoImpl(Id userId, boolean showVotes, long votesTime) {
    this.userId = userId;
    this.showVotes = showVotes;
    if (votesTime == 0) {
      this.votesTime = BaseEnvironment.getEnvironment().getTime().getTimeMillis();
    } else {
      this.votesTime = votesTime;
    }
  }

  public GetUserInfoResult genLoad() {
    EntityUser user = EntityUser.load(userId);
    EntityUser delegate = null;
    List<EntityUser> userVotes = null;

    if (showVotes) {
      userVotes = getVotes(user);
      HashoutUserToDelegate hashoutUserToDelegate = new HashoutUserToDelegate();
      List<EntityUserAndVote> userDelegateVotes =
        VoteFilter.filterVotes(hashoutUserToDelegate.loadEntities(userId.getKey()), votesTime);
      if (userDelegateVotes.size() > 1) {
        throw new GetUserInfoException("More then one delegate: " + userId);
      }
      if (userDelegateVotes.size() == 1) {
        delegate = userDelegateVotes.get(0).getUser();
      }
    }
    List<UserActionResult> userActions = genUserActions(userId);

    return new GetUserInfoResult(user, userActions, delegate, userVotes);
  }

  private List<UserActionResult> genUserActions(Id userId) {
    HashoutUserToAction hashoutUserToAction = new HashoutUserToAction();
    List<EntityAction> userActions = hashoutUserToAction.loadEntities(userId.getKey());
    List<UserActionResult> userActionResults = new ArrayList<UserActionResult>();
    for (EntityAction action : userActions) {
      userActionResults.add(genUserAction(action));
    }
    return userActionResults;
  }

  private UserActionResult genUserAction(EntityAction action) {
    UserActionResult userActionResult = new UserActionResult();
    userActionResult.setActionType(action.getActionType());
    userActionResult.setTime(action.getTime());
    Id actorId = action.getActorId();
    if (actorId != null) {
      userActionResult.setActor(EntityUser.load(actorId));
    }
    userActionResult.setUserType(action.getUserType());
    Id documentId = action.getDocumentId();
    if (documentId != null) {
      userActionResult.setDocument(EntityDocument.load(documentId));
    }
    Id delegateId = action.getDelegateId();
    if (delegateId != null) {
      userActionResult.setDelegate(EntityUser.load(delegateId));
    }
    Id voteId = action.getVoteId();
    if (voteId != null) {
      userActionResult.setVote(EntityVote.load(voteId));
    }
    userActionResult.setVoteDecision(action.getVoteDecision());
    return userActionResult;
  }

  private List<EntityUser> getVotes(EntityUser user) {
    HashoutDelegateToUser hashoutDelegateToUser = new HashoutDelegateToUser();
    List<EntityUserAndVote> userAndVotes =
      VoteFilter.filterVotes(hashoutDelegateToUser.loadEntities(user.getId().getKey()), votesTime);
    List<EntityUser> userVotes = new ArrayList<EntityUser>();
    for (EntityUserAndVote userAndVote : userAndVotes) {
      userVotes.add(userAndVote.getUser());
    }
    return userVotes;
  }
}
