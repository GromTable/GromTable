package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;

public class EntityAction extends EntityObject<EntityAction> {
  private ActionType type;
  private long time;
  private Id voterId;
  private Id choiceId;
  private Id documentId;
  private VoteDocumentDecision voteDecision;

  public EntityAction(ActionType type) {
    this.type = type;
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_ACTION;
  }

  public ActionType getType() {
    return type;
  }

  public long getTime() {
    return time;
  }

  public Id getVoterId() {
    return voterId;
  }

  public Id getChoiceId() {
    return choiceId;
  }

  public Id getDocumentId() {
    return documentId;
  }

  public VoteDocumentDecision getVoteDecision() {
    return voteDecision;
  }

  public static EntityAction load(final Id id) {
    return EntityObject.load(id, EntityAction.class);
  }
}
