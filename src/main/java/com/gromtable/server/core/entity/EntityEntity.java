package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;

public class EntityEntity extends EntityObject<EntityEntity> {
  private String name = null;

  public EntityEntity(Id id, String name) {
    this.setId(id);
    this.setName(name);
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_ENTITY;
  }

  public static EntityEntity load(final Id id) {
    return EntityObject.load(id, EntityEntity.class);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
