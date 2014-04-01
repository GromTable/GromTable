package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;

/**
 * This entity represents an action taken by user. This will be stored in user history.
 */
public class EntityAction extends EntityObject<EntityAction> {
  private ActionType actionType;
  private long time;
  private Id actorId;
  private UserType userType;
  private Id documentId;
  private Id delegateId;
  private Id voteId;
  private VoteDocumentDecision voteDecision;

  public EntityAction(ActionType actionType, long time, Id actorId) {
    this.actionType = actionType;
    this.time = time;
    this.actorId = actorId;
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_ACTION;
  }

  public ActionType getActionType() {
    return actionType;
  }

  public long getTime() {
    return time;
  }

  public Id getActorId() {
    return actorId;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setDocumentId(Id documentId) {
    this.documentId = documentId;
  }

  public Id getDocumentId() {
    return documentId;
  }

  public void setDelegateId(Id delegateId) {
    this.delegateId = delegateId;
  }

  public Id getDelegateId() {
    return delegateId;
  }

  public void setVoteId(Id voteId) {
    this.voteId = voteId;
  }

  public Id getVoteId() {
    return voteId;
  }

  public void setVoteDocumentDecision(VoteDocumentDecision voteDecision) {
    this.voteDecision = voteDecision;
  }

  public VoteDocumentDecision getVoteDecision() {
    return voteDecision;
  }

  public static EntityAction load(final Id id) {
    return EntityObject.load(id, EntityAction.class);
  }
}
