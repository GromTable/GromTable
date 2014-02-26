package com.gromtable.server.core.data;

import java.util.Random;

import junit.framework.Assert;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

public class IdTest {

  private void assertId(Id id, int dbId, int typeId, long sequenceId) {
    Assert.assertNotNull(id);
    Assert.assertTrue(id.isValid());
    Assert.assertEquals(id.getDbId(), dbId);
    Assert.assertEquals(id.getTypeId(), typeId);
    Assert.assertEquals(id.getSequenceId(), sequenceId);
  }

  @Test
  public void genIdForDbTest() {
    Id id = Id.genIdForDb(1, 2, 3);
    assertId(id, 1, 2, 3);
  }

  @Test
  public void genIdForDbLimitTest() {
    Id idMin = Id.genIdForDb(Id.MIN_DB_ID, Id.MIN_TYPE_ID, Id.MIN_SEQUENCE_ID);
    assertId(idMin, Id.MIN_DB_ID, Id.MIN_TYPE_ID, Id.MIN_SEQUENCE_ID);
    Id idMin10 = Id.genIdForDb(Id.MIN_DB_ID + 10, Id.MIN_TYPE_ID + 10, Id.MIN_SEQUENCE_ID + 10);
    assertId(idMin10, Id.MIN_DB_ID + 10, Id.MIN_TYPE_ID + 10, Id.MIN_SEQUENCE_ID + 10);

    Id idMax = Id.genIdForDb(Id.MAX_DB_ID, Id.MAX_TYPE_ID, Id.MAX_SEQUENCE_ID);
    assertId(idMax, Id.MAX_DB_ID, Id.MAX_TYPE_ID, Id.MAX_SEQUENCE_ID);
    Id idMax10 = Id.genIdForDb(Id.MAX_DB_ID - 10, Id.MAX_TYPE_ID - 10, Id.MAX_SEQUENCE_ID - 10);
    assertId(idMax10, Id.MAX_DB_ID - 10, Id.MAX_TYPE_ID - 10, Id.MAX_SEQUENCE_ID - 10);
  }

  @Test
  public void toStringTest() {
    Id id = Id.genIdForDb(1, 2, 3);
    String base16 = id.getString();
    Assert.assertEquals(base16, "00010002f60f94d60000000000000003");
  }

  @Test
  public void fromBase16Test() throws DecoderException {
    Id id = Id.genIdForDb(1, 2, 3);
    String base16 = id.getString();
    Assert.assertEquals(Id.SIZE * 2, base16.length());

    Id fromStrId = Id.fromKey(base16);
    Assert.assertEquals(id.toString(), fromStrId.toString());
    Assert.assertTrue(id.equals(fromStrId));
  }

  @Test
  public void isValidTest() {
    Random random = new Random();
    for (int i = 0; i < 1000; i++) {
      byte[] data = new byte[Id.SIZE];
      random.nextBytes(data);
      Id id = new Id(data);
      Assert.assertFalse(id.isValid());
    }
  }

  @Test
  public void performanceGenIdForDbTest() {
    for (int i = 0; i < 1000; i++) {
      Id id = Id.genIdForDb(i, i + 1, i + 2);
      assertId(id, i, i + 1, i + 2);
    }
  }
}
