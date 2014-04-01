package com.gromtable.server.api.voteuser;

import java.util.ArrayList;
import java.util.List;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Pair;
import com.gromtable.server.core.entity.ActionType;
import com.gromtable.server.core.entity.EntityAction;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityUserAndVote;
import com.gromtable.server.core.entity.EntityVote;
import com.gromtable.server.core.entity.UserType;
import com.gromtable.server.core.entity.VoteType;
import com.gromtable.server.core.entity.VoteUserDecision;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.Environment;
import com.gromtable.server.core.hashout.HashoutDelegateToUser;
import com.gromtable.server.core.hashout.HashoutUserToAction;
import com.gromtable.server.core.hashout.HashoutUserToDelegate;
import com.gromtable.server.core.loader.Loader;

public class VoteUserImpl extends Loader<VoteUserResult> {
  private Id voterId;
  private Id delegateId;
  private VoteUserDecision voteDecision;

  public VoteUserImpl(Id voterId, Id delegateId, VoteUserDecision voteDecision) {
    this.voterId = voterId;
    this.delegateId = delegateId;
    this.voteDecision = voteDecision;
  }

  private List<EntityVote> verifyAction(HashoutUserToDelegate hashoutUserToDelegate, long time) {
    Pair<EntityUser> users = EntityUser.load(new Pair<Id>(voterId, delegateId));
    if (!users.getFirst().getType().equals(UserType.VOTER)) {
      throw new VoteUserException("Voter type " + users.getFirst().getType() + ": " + voterId);
    }
    if (!users.getSecond().getType().equals(UserType.DELEGATE)) {
      throw new VoteUserException("Delegate is not of type delegate: " + delegateId);
    }
    List<EntityUserAndVote> currentVotes = hashoutUserToDelegate.loadEntities(voterId.getKey());
    List<EntityVote> activeVotes = new ArrayList<EntityVote>();
    for (EntityUserAndVote userAndVote : currentVotes) {
      EntityVote vote = userAndVote.getVote();
      if (vote.getEndTime() > time) {
        activeVotes.add(vote);
      }
    }
    if (activeVotes.size() > 1) {
      throw new VoteUserException("More then one current vote: " + voterId + " " + delegateId);
    }
    return activeVotes;
  }

  public VoteUserResult genLoad() {
    Environment environment = BaseEnvironment.getEnvironment();
    long time = environment.getTime().getTimeMillis();
    HashoutUserToDelegate hashoutUserToDelegate = new HashoutUserToDelegate();
    HashoutDelegateToUser hashoutDelegateToUser = new HashoutDelegateToUser();
    HashoutUserToAction hashoutUserToAction = new HashoutUserToAction();

    List<EntityVote> activeVotes = verifyAction(hashoutUserToDelegate, time);

    for (EntityVote vote : activeVotes) {
      vote.setEndTime(time);
      vote.save();
    }
    EntityVote newVote = new EntityVote(VoteType.USER, voterId, delegateId, time);
    newVote.setVoteUserDecision(voteDecision);
    newVote.saveToDb(voterId.getDbId());
    EntityAction newAction = new EntityAction(ActionType.VOTE_USER, time, voterId);
    newAction.setDelegateId(delegateId);
    newAction.setVoteId(newVote.getId());
    newAction.saveToDb(voterId.getDbId());
    hashoutUserToDelegate.addKey(voterId.getKey(), getKey(delegateId, newVote.getId()));
    hashoutDelegateToUser.addKey(delegateId.getKey(), getKey(voterId, newVote.getId()));
    hashoutUserToAction.addKey(voterId.getKey(), newAction.getId().getKey());
    return new VoteUserResult(true, newVote.getId());
  }

}
