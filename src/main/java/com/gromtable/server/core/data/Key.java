package com.gromtable.server.core.data;

import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

public abstract class Key implements Comparable<Key> {
  public abstract byte[] getRowData();

  public boolean equals(Object obj) {
    Key other = (Key) obj;
    return Arrays.equals(getRowData(), other.getRowData());
  }

  public int hashCode() {
    return Arrays.hashCode(getRowData());
  }

  public int compareTo(Key key) {
    byte[] data1 = getRowData();
    byte[] data2 = key.getRowData();
    return new String(data1).compareTo(new String(data2));
  }

  public String stringValue() {
    return new String(getRowData());
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Key(");
    buffer.append("hex=" + Hex.encodeHexString(getRowData()) + ",");
    buffer.append("str=" + new String(getRowData()) + ",");
    buffer.append(")");
    return buffer.toString();
  }
}
