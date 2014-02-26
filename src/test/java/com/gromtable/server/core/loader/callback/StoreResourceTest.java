package com.gromtable.server.core.loader.callback;

import java.io.IOException;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.loader.base.CounterLoader;
import com.gromtable.server.core.loader.base.HashoutAddLoader;
import com.gromtable.server.core.loader.base.HashoutGetLoader;

import junit.framework.Assert;

import org.junit.Test;

public abstract class StoreResourceTest extends BaseTest {
  private static final Id HASHOUT_ID = Id.genIdForDb(0, 0, 1);
  private static final Key FROM_ID = Id.genIdForDb(0, 0, 2).getKey();
  private static final Key TO_ID = Id.genIdForDb(0, 0, 3).getKey();
  private static final Key TO_ID2 = Id.genIdForDb(0, 0, 4).getKey();
  private static final Data DATA = new Data("data".getBytes());
  private static final Data DATA2 = new Data("data2".getBytes());

  public abstract StoreResource getStoreResource();

  @Test
  public void addTest() throws IOException {
    StoreResource storeResource = getStoreResource();
    getTestEnvironment().setStore(storeResource);

    HashoutAddLoader addLoader = new HashoutAddLoader(HASHOUT_ID, FROM_ID, TO_ID, DATA);
    addLoader.genLoad();

    HashoutGetLoader getLoader = new HashoutGetLoader(HASHOUT_ID, FROM_ID, TO_ID);
    Assert.assertEquals(getLoader.genLoad(), DATA);
  }

  @Test
  public void addMultipleSerialTest() throws IOException {
    StoreResource storeResource = getStoreResource();
    getTestEnvironment().setStore(storeResource);
    new HashoutAddLoader(HASHOUT_ID, FROM_ID, TO_ID, DATA).genLoad();

    new HashoutAddLoader(HASHOUT_ID, FROM_ID, TO_ID2, DATA2).genLoad();

    HashoutGetLoader getLoader = new HashoutGetLoader(HASHOUT_ID, FROM_ID, TO_ID);
    HashoutGetLoader getLoader2 = new HashoutGetLoader(HASHOUT_ID, FROM_ID, TO_ID2);
    Assert.assertEquals(getLoader.genLoad(), DATA);
    Assert.assertEquals(getLoader2.genLoad(), DATA2);
  }

  @Test
  public void serialCounterTest() throws IOException {
    StoreResource storeResource = getStoreResource();
    getTestEnvironment().setStore(storeResource);
    long sum = 0;
    for (int i = 0; i < 100; i++) {
      long amount = i + 1;
      sum += amount;
      Key rowKey = new Key("ab");
      Key columnKey = new Key("cd");
      CounterLoader counterLoader = new CounterLoader(rowKey, columnKey, amount);
      Assert.assertEquals(sum, counterLoader.genLoad().longValue());
    }
  }
}
