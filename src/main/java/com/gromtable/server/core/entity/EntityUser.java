package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;

public class EntityUser extends EntityObject<EntityUser> {
  private String fbId;
  private String name;

  public EntityUser(String fbId, String name) {
    this.fbId = fbId;
    this.name = name;
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_USER;
  }

  public String getFbId() {
    return fbId;
  }

  public String getName() {
    return name;
  }

  public static EntityUser load(final Id id) {
    return EntityObject.load(id, EntityUser.class);
  }
}
