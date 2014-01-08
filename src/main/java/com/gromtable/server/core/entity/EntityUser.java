package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Pair;

public class EntityUser extends EntityObject<EntityUser> {
  private UserType type;
  private String fbId;
  private String name;

  public EntityUser(String fbId, String name) {
    this.type = UserType.VOTER;
    this.fbId = fbId;
    this.name = name;
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_USER;
  }

  public void setType(UserType type) {
    this.type = type;
  }

  public UserType getType() {
    return type;
  }

  public String getFbId() {
    return fbId;
  }

  public String getTestId() {
    return "" + name.hashCode();
  }

  public String getName() {
    return name;
  }

  public static EntityUser load(final Id id) {
    return EntityObject.load(id, EntityUser.class);
  }

  public static Pair<EntityUser> load(final Pair<Id> ids) {
    return EntityObject.load(ids, EntityUser.class);
  }
}
