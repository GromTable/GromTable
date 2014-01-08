package com.gromtable.server.core.loader;

import java.nio.ByteBuffer;

import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.RowKey;

public abstract class Loader<T> {
  public static final byte DELIMITER = (byte) ':';

  public abstract T genLoad();

  protected static Key getKey(Key[] keys) {
    int capacity = 0;
    for (Key key : keys) {
      capacity += key.getRowData().length + 1;
    }
    capacity -= 1;
    ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
    boolean afterKey = false;
    for (Key key : keys) {
      if (afterKey) {
        byteBuffer.put(DELIMITER);
      } else {
        afterKey = true;
      }
      byteBuffer.put(key.getRowData());
    }
    return new RowKey(byteBuffer.array());
  }

  protected static Key getKey(Key id1, Key id2) {
    return getKey(new Key[] {id1, id2});
  }
}
