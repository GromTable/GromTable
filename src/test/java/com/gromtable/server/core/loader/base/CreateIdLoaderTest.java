package com.gromtable.server.core.loader.base;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.data.Id;

public class CreateIdLoaderTest extends BaseTest {
  @Test
  public void generateDifferentTest() {
    Set<Id> ids = new HashSet<Id>();
    int size = 100;
    int dbId = 1;
    int typeId = 2;
    for (int i = 0; i < size; i++) {
      ids.add(new CreateIdLoader(dbId, typeId).genLoad());
    }
    Assert.assertEquals(ids.size(), size);
  }
}
