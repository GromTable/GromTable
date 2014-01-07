package com.gromtable.server.core.entity;

import junit.framework.Assert;

import org.junit.Test;

public class EntityUserTest extends EntityObjectTest<EntityUser> {
  protected EntityObject<EntityUser> getEntityObject() {
    return new EntityUser("1", "Test user");
  }

  protected Class<EntityUser> getEntityObjectClass() {
    return EntityUser.class;
  }

  @Test
  public void genWriteCreateUserTest() {
    EntityUser savedUser = new EntityUser("1", "Test user").save();
    EntityUser user = EntityUser.load(savedUser.getId());
    Assert.assertEquals(savedUser.getName(), "Test user");
    Assert.assertEquals(user.getName(), "Test user");
    Assert.assertEquals(savedUser.getFbId(), "1");
    Assert.assertEquals(user.getFbId(), "1");
  }
}
