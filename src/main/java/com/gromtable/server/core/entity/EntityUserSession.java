package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;


public class EntityUserSession extends EntityObject<EntityUserSession> {
  public enum Status {
    LOGED_IN,
    LOGED_OUT
  }

  private Id userId;
  private Status status;

  public EntityUserSession(Id userId, Status status) {
    this.userId = userId;
    this.status = status;
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_USER_SESSION;
  }

  public Id getUserId() {
    if (status == Status.LOGED_IN) {
      return userId;
    } else {
      return null;
    }
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public static EntityUserSession load(final Id id) {
    return EntityObject.load(id, EntityUserSession.class);
  }
}
