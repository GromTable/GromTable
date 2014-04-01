package com.gromtable.server.api.getuserinfo;

import com.gromtable.server.core.entity.ActionType;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityVote;
import com.gromtable.server.core.entity.UserType;
import com.gromtable.server.core.entity.VoteDocumentDecision;

public class UserActionResult {
  private ActionType actionType;
  private long time;
  private EntityUser actor;
  private UserType userType;
  private EntityDocument document;
  private EntityUser delegate;
  private EntityVote vote;
  private VoteDocumentDecision voteDecision;

  public UserActionResult() {
  }

  public ActionType getActionType() {
    return actionType;
  }

  public void setActionType(ActionType actionType) {
    this.actionType = actionType;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public EntityUser getActor() {
    return actor;
  }

  public void setActor(EntityUser actor) {
    this.actor = actor;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public EntityDocument getDocument() {
    return document;
  }

  public void setDocument(EntityDocument document) {
    this.document = document;
  }

  public EntityUser getDelegate() {
    return delegate;
  }

  public void setDelegate(EntityUser delegate) {
    this.delegate = delegate;
  }

  public EntityVote getVote() {
    return vote;
  }

  public void setVote(EntityVote vote) {
    this.vote = vote;
  }

  public VoteDocumentDecision getVoteDecision() {
    return voteDecision;
  }

  public void setVoteDecision(VoteDocumentDecision voteDecision) {
    this.voteDecision = voteDecision;
  }

  public String toString() {
    return actionType.toString() + " " + time;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public boolean equals(Object other) {
    return other.toString().equals(toString());
  }
}
