package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;

/**
 * Quick and dirty object, for some custom
 */
public class EntityJson extends EntityObject<EntityJson> {
  private String name;
  private String value;

  public EntityJson(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_JSON;
  }


  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public static EntityJson load(final Id id) {
    return EntityObject.load(id, EntityJson.class);
  }
}
