package com.gromtable.server.core.entity;

public enum EntityType {
  ENTITY_ENTITY(1),
  ENTITY_HASHOUT(3),
  ENTITY_USER(5),
  ENTITY_USER_SESSION(6),
  ENTITY_COORDINATE(9);

  private final long id;

  EntityType(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }
}
