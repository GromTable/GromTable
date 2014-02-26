package com.gromtable.server.core.loader;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;

public abstract class Loader<T> {
  public static final String DELIMITER = ":";

  public abstract T genLoad();

  protected static Key getKey(Key[] keys) {
    StringBuffer buffer = new StringBuffer();
    for (Key key : keys) {
      if (buffer.length() != 0) {
        buffer.append(DELIMITER);
      }
      buffer.append(key.toString());
    }
    return new Key(buffer.toString());
  }

  protected static Key getKey(Key id1, Key id2) {
    return new Key(id1 + DELIMITER + id2);
  }

  protected static Key getKey(Id id1, Id id2) {
    return getKey(id1.getKey(), id2.getKey());
  }
}
