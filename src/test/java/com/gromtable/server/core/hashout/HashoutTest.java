package com.gromtable.server.core.hashout;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.entity.EntityObject;

public abstract class HashoutTest<T extends EntityObject<T>> extends BaseTest {
  protected abstract Hashout<T> getHashout();

  protected abstract T getEntityObject();

  @Test
  public void addKey() {
    Hashout<T> hashout = getHashout();
    Key fromKey = Id.genIdForDb(0, 0, 1);
    Key toKey = Id.genIdForDb(0, 0, 2);
    hashout.addKey(fromKey, toKey);
    List<HashoutRecord> loadedEntries = hashout.loadRecords(fromKey);
    Assert.assertEquals(loadedEntries.size(), 1);
    Assert.assertEquals(loadedEntries.get(0).getKey(), toKey);
  }

  @Test
  public void addEntity() {
    Hashout<T> hashout = getHashout();
    Key fromKey = Id.genIdForDb(0, 0, 1);
    T entity = getEntityObject();
    hashout.addEntity(fromKey, entity);
    T loadedEntity = hashout.loadEntity(fromKey);
    Assert.assertEquals(entity, loadedEntity);
  }
}
