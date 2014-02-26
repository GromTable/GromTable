package com.gromtable.server.core.data;

import junit.framework.Assert;

import org.junit.Test;

public class KeyTest {
  private static final byte[] DATA = new byte[] {1, 2, 3};
  private static final byte[] DATA_SAME = new byte[] {1, 2, 3};
  private static final byte[] DATA_DIFF = new byte[] {1, 2, 4};

  @Test
  public void hashTest() {
    Assert.assertEquals(new Key(DATA).hashCode(), new Key(DATA).hashCode());
    Assert.assertEquals(new Key(DATA).hashCode(), new Key(DATA_SAME).hashCode());

    Assert.assertFalse(new Key(DATA).hashCode() == new Key(DATA_DIFF).hashCode());
  }

  @Test
  public void equalsTest() {
    Assert.assertEquals(new Key(DATA), new Key(DATA));
    Assert.assertEquals(new Key(DATA), new Key(DATA_SAME));

    Assert.assertFalse(new Key(DATA).equals(new Key(DATA_DIFF)));
  }
}
