package com.gromtable.server.core.loader.base;

import junit.framework.Assert;

import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Id;

public class HashoutAddLoaderTest extends BaseTest {
  @Test
  public void addTest() {
    Id hashoutId = Id.genIdForDb(0, 0, 1);
    Id fromKey = Id.genIdForDb(0, 0, 2);
    Id toId = Id.genIdForDb(0, 0, 3);
    Data savedData = new Data(new byte[] {'d', 'a', 't', 'a'});
    new HashoutAddLoader(hashoutId, fromKey.getKey(), toId.getKey(), savedData).genLoad();
    Data loadedData = new HashoutGetLoader(hashoutId, fromKey.getKey(), toId.getKey()).genLoad();
    Assert.assertEquals(savedData, loadedData);
  }

  @Test
  public void addMultipleSerialTest() {
    Id hashoutId = Id.genIdForDb(0, 0, 1);
    Id fromKey = Id.genIdForDb(0, 0, 2);
    Id toId1 = Id.genIdForDb(0, 0, 3);
    Id toId2 = Id.genIdForDb(0, 0, 4);
    Data savedData1 = new Data(new byte[] {'d', 'a', 't', 'a', '1'});
    Data savedData2 = new Data(new byte[] {'d', 'a', 't', 'a', '2'});
    new HashoutAddLoader(hashoutId, fromKey.getKey(), toId1.getKey(), savedData1).genLoad();
    new HashoutAddLoader(hashoutId, fromKey.getKey(), toId2.getKey(), savedData2).genLoad();
    Data loadedData1 = new HashoutGetLoader(hashoutId, fromKey.getKey(), toId1.getKey()).genLoad();
    Data loadedData2 = new HashoutGetLoader(hashoutId, fromKey.getKey(), toId2.getKey()).genLoad();
    Assert.assertEquals(loadedData1, savedData1);
    Assert.assertEquals(loadedData2, savedData2);
  }
}
