package com.gromtable.server.api.getdocumentinfo;

import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityUserAndVote;
import com.gromtable.server.core.entity.VoteDocumentDecision;

public class UserAndDecision {
  private final EntityUser user;
  private final VoteDocumentDecision decision;

  public UserAndDecision(EntityUser user, VoteDocumentDecision decision) {
    this.user = user;
    this.decision = decision;
  }

  public UserAndDecision(EntityUserAndVote userAndVote) {
    this(userAndVote.getUser(), userAndVote.getVote().getVoteDocumentDecision());
  }

  public EntityUser getUser() {
    return user;
  }

  public VoteDocumentDecision getDecision() {
    return decision;
  }
}
