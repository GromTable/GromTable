package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;

public class EntityHashout extends EntityObject<EntityHashout> {

  private long fromTypeId;

  private long toTypeId;

  private String name = null;

  public EntityHashout(long fromTypeId, long toTypeId, String name) {
    this.setFromTypeId(fromTypeId);
    this.setToTypeId(toTypeId);
    this.setName(name);
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_HASHOUT;
  }

  public static EntityHashout load(final Id id) {
    return EntityHashout.load(id, EntityHashout.class);
  }

  public long getFromTypeId() {
    return fromTypeId;
  }

  public void setFromTypeId(long fromTypeId) {
    this.fromTypeId = fromTypeId;
  }

  public long getToTypeId() {
    return toTypeId;
  }

  public void setToTypeId(long toTypeId) {
    this.toTypeId = toTypeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
