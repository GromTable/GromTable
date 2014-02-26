package com.gromtable.server.core.data;

import java.nio.ByteBuffer;

public class Data implements Comparable<Data> {
  public static final Data EMPTY = new Data("");
  private final String str;

  public Data(String str) {
    this.str = str;
  }

  public Data(byte[] data) {
    this.str = new String(data);
  }

  public Data(long number) {
    this.str = Long.toString(number);
  }

  public static Data fromLongBytes(byte[] bytes) {
    ByteBuffer buf = ByteBuffer.wrap(bytes);
    return new Data(buf.getLong());
  }

  public int hashCode() {
    return str.hashCode();
  }

  public boolean equals(Object obj) {
    return str.equals(obj.toString());
  }

  public int compareTo(Data data) {
    return str.compareTo(data.toString());
  }

  public String toString() {
    return str;
  }

  public Long asLong() {
    if (str.length() == 0) {
      return new Long(0);
    }
    return Long.parseLong(str);
  }

  public Data addData(Data other) {
    return new Data(asLong() + other.asLong());
  }

  public byte[] getBytes() {
    return str.getBytes();
  }

  public String getString() {
    return str;
  }
}
