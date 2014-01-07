package com.gromtable.server.core.entity;

import junit.framework.Assert;

import org.junit.Test;

import com.gromtable.server.base.BaseTest;

public abstract class EntityObjectTest<T extends EntityObject<T>> extends BaseTest {
  protected abstract EntityObject<T> getEntityObject();

  protected abstract Class<T> getEntityObjectClass();

  @Test
  public void saveLoadTest() {
    EntityObject<T> originalEntityObject = getEntityObject();
    EntityObject<T> savedEntityObject = originalEntityObject.save();

    Assert.assertSame(originalEntityObject, savedEntityObject);
    EntityObject<T> loadedEntityObject = EntityObject.load(savedEntityObject.getId(),
        getEntityObjectClass());
    Assert.assertNotSame(savedEntityObject, loadedEntityObject);
    Assert.assertEquals(savedEntityObject, loadedEntityObject);
  }
}
