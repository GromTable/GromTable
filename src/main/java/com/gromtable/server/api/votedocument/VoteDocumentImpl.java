package com.gromtable.server.api.votedocument;

import java.util.ArrayList;
import java.util.List;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.entity.EntityVote;
import com.gromtable.server.core.entity.VoteDocumentDecision;
import com.gromtable.server.core.entity.VoteType;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.Environment;
import com.gromtable.server.core.hashout.HashoutDocumentToUser;
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
    long time = environment.getTime().getNanoTime();
    HashoutUserToDocument hashoutUserToDocument = new HashoutUserToDocument();
    HashoutDocumentToUser hashoutDocumentToUser = new HashoutDocumentToUser();
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
    hashoutUserToDocument.addEntity(getKey(voterId, documentId), newVote);
    hashoutDocumentToUser.addKey(documentId.getKey(), getKey(voterId, newVote.getId()));
    return new VoteDocumentResult(true, newVote.getId());
  }
}
