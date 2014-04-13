package com.gromtable.server.core.entity;

public enum EntityType {
  ENTITY_ENTITY(1),
  ENTITY_HASHOUT(3),
  ENTITY_USER(5),
  ENTITY_USER_SESSION(6),
  ENTITY_DOCUMENT(7),
  ENTITY_VOTE(8),
  ENTITY_ACTION(9),
  ENTITY_LIST(10),
  ENTITY_JSON(11),
  ENTITY_COUNTERS(12);

  private final long id;

  EntityType(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }
}
