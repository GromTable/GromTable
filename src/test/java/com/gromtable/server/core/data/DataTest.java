package com.gromtable.server.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.Assert;

import org.junit.Test;

public class DataTest {
  private void testMap(Map<Data, Data> data) {
    Assert.assertEquals(0, data.size());
    for (int i = 0; i < 10; i++) {
      data.put(new Data(i), new Data(2 * i));
    }
    Assert.assertEquals(10, data.size());
    for (int i = 0; i < 10; i++) {
      Assert.assertEquals(new Data(2 * i), data.get(new Data(i)));
    }
    for (int i = 0; i < 10; i++) {
      data.put(new Data(i), new Data(3 * i));
    }
    Assert.assertEquals(10, data.size());
    for (int i = 0; i < 10; i++) {
      Assert.assertEquals(new Data(3 * i), data.get(new Data(i)));
    }
  }

  @Test
  public void testTreeMap() {
    testMap(new TreeMap<Data, Data>());
  }

  @Test
  public void testHashMap() {
    testMap(new HashMap<Data, Data>());
  }

  @Test
  public void testAddData() {
    long x = 345354325234L;
    long y = 452345342523L;
    Assert.assertEquals(x + y, new Data(x).addData(new Data(y)).asLong().longValue());
  }

  @Test
  public void testToString() {
    Data data = new Data("datadata".getBytes());
    Assert.assertEquals("Data(hex=6461746164617461,long=7233190438178485345,str=datadata)", data.toString());

    Data dataLong = new Data(7233190438178485345L);
    Assert.assertEquals("Data(hex=6461746164617461,long=7233190438178485345,str=datadata)", dataLong.toString());
  }
}
