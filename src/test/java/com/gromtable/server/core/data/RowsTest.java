package com.gromtable.server.core.data;

import junit.framework.Assert;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

public class RowsTest {
  private void put(Rows rows, String row, String column, String data) {
    rows.put(new Key(row), new Key(column), new Data(data));
  }
  private Rows getExampleRows() {
    Rows rows = new Rows();
    for (int rowId = 0; rowId < 5; rowId++) {
      for (int columnId = 0; columnId < 5; columnId++) {
        put(rows, "row" + rowId, "column" + columnId, "data" + columnId);
      }
    }
    return rows;
  }

  @Test
  public void testSerialize() throws DecoderException {
    Rows rows = getExampleRows();
    String serializedRows = rows.serializeRow();
    Rows deserializedRows = Rows.createFromString(serializedRows);
    Assert.assertEquals(rows, deserializedRows);
  }

  @Test
  public void testEquals() throws DecoderException {
    Rows rows1 = new Rows();
    Rows rows2 = new Rows();
    Rows rows3 = new Rows();
    put(rows1, "x", "y", "z");
    put(rows2, "x", "y", "z");
    put(rows3, "x", "y", "y");
    Assert.assertTrue(rows1.equals(rows2));
    Assert.assertFalse(rows1.equals(rows3));
  }
}
