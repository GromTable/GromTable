package com.gromtable.server.core.data;

import junit.framework.Assert;

import org.junit.Test;

public class KeyTest {
  private static final byte[] DATA = new byte[] {1, 2, 3};
  private static final byte[] DATA_SAME = new byte[] {1, 2, 3};
  private static final byte[] DATA_DIFF = new byte[] {1, 2, 4};

  @Test
  public void hashTest() {
    Assert.assertEquals(new RowKey(DATA).hashCode(), new RowKey(DATA).hashCode());
    Assert.assertEquals(new RowKey(DATA).hashCode(), new RowKey(DATA_SAME).hashCode());

    Assert.assertFalse(new RowKey(DATA).hashCode() == new RowKey(DATA_DIFF).hashCode());
  }

  @Test
  public void equalsTest() {
    Assert.assertEquals(new RowKey(DATA), new RowKey(DATA));
    Assert.assertEquals(new RowKey(DATA), new RowKey(DATA_SAME));

    Assert.assertFalse(new RowKey(DATA).equals(new RowKey(DATA_DIFF)));
  }
}
