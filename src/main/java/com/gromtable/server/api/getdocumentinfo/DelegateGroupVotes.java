package com.gromtable.server.api.getdocumentinfo;

import java.util.ArrayList;
import java.util.List;

import com.gromtable.server.core.entity.EntityUserAndVote;

public class DelegateGroupVotes {
  private final UserAndDecision delegateVote;
  private final DecisionsMap groupVotes;
  private final List<UserAndDecision> usersVote;

  public DelegateGroupVotes(UserAndDecision delegateVote) {
    this.delegateVote = delegateVote;
    this.groupVotes = new DecisionsMap();
    this.usersVote = new ArrayList<UserAndDecision>();
  }

  public UserAndDecision getDelegateVote() {
    return delegateVote;
  }

  public DecisionsMap getGroupVotes() {
    return groupVotes;
  }

  public List<UserAndDecision> getUsersVote() {
    return usersVote;
  }

  public void addUser(EntityUserAndVote user) {
    if (user != null) {
      UserAndDecision userAndDecision = new UserAndDecision(user);
      if (delegateVote != null) {
        groupVotes.subtruct(delegateVote);
      }
      groupVotes.add(userAndDecision);
      usersVote.add(userAndDecision);
    }
  }
}
