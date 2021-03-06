package com.gromtable.server.api.votedocument;

import java.util.ArrayList;
import java.util.List;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.entity.ActionType;
import com.gromtable.server.core.entity.EntityAction;
import com.gromtable.server.core.entity.EntityVote;
import com.gromtable.server.core.entity.VoteDocumentDecision;
import com.gromtable.server.core.entity.VoteType;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.Environment;
import com.gromtable.server.core.hashout.HashoutDocumentToUser;
import com.gromtable.server.core.hashout.HashoutUserToAction;
import com.gromtable.server.core.hashout.HashoutUserToDocument;
import com.gromtable.server.core.loader.Loader;

public class VoteDocumentImpl extends Loader<VoteDocumentResult> {
  private Id voterId;
  private Id documentId;
  private VoteDocumentDecision voteDecision;

  public VoteDocumentImpl(Id voterId, Id documentId, VoteDocumentDecision voteDecision) {
    this.voterId = voterId;
    this.documentId = documentId;
    this.voteDecision = voteDecision;
  }

  public VoteDocumentResult genLoad() {
    Environment environment = BaseEnvironment.getEnvironment();
    long time = environment.getTime().getTimeMillis();
    HashoutUserToDocument hashoutUserToDocument = new HashoutUserToDocument();
    HashoutDocumentToUser hashoutDocumentToUser = new HashoutDocumentToUser();
    HashoutUserToAction hashoutUserToAction = new HashoutUserToAction();
    Key key = getKey(voterId, documentId);
    List<EntityVote> currentVotes = hashoutUserToDocument.loadEntities(key);
    List<EntityVote> activeVotes = new ArrayList<EntityVote>();
    for (EntityVote vote : currentVotes) {
      if (vote.getEndTime() > time) {
        activeVotes.add(vote);
      }
    }
    if (activeVotes.size() > 1) {
      throw new VoteDocumentException("More then one current vote: " + voterId + " " + documentId);
    }
    for (EntityVote vote : activeVotes) {
      vote.setEndTime(time);
      vote.save();
    }

    EntityVote newVote = new EntityVote(VoteType.DOCUMENT, voterId, documentId, time);
    newVote.setVoteDocumentDecision(voteDecision);
    newVote.saveToDb(voterId.getDbId());
    EntityAction newAction = new EntityAction(ActionType.VOTE_DOCUMENT, time, voterId);
    newAction.setDocumentId(documentId);
    newAction.setVoteId(newVote.getId());
    newAction.setVoteDocumentDecision(voteDecision);
    newAction.saveToDb(voterId.getDbId());
    hashoutUserToDocument.addKey(getKey(voterId, documentId), newVote.getId().getKey());
    hashoutDocumentToUser.addKey(documentId.getKey(), getKey(voterId, newVote.getId()));
    hashoutUserToAction.addKey(voterId.getKey(), newAction.getId().getKey());
    return new VoteDocumentResult(true, newVote.getId());
  }
}
