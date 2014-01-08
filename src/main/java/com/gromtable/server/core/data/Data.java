package com.gromtable.server.core.data;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

public class Data implements Comparable<Data> {
  public static final Data EMPTY = new Data(new byte[0]);
  private final byte[] data;

  public Data(byte[] data) {
    this.data = data;
  }

  public Data(long data) {
    this.data = longToBytes(data);
  }

  private static long bytesToLong(byte[] x) {
    ByteBuffer buf = ByteBuffer.wrap(x);
    return buf.getLong();
  }

  private static byte[] longToBytes(long x) {
    byte b[] = new byte[8];

    ByteBuffer buf = ByteBuffer.wrap(b);
    buf.putLong(x);
    return b;
  }

  public byte[] getRowData() {
    return data;
  }

  public int hashCode() {
    return Arrays.hashCode(data);
  }

  public boolean equals(Object obj) {
    Data other = (Data) obj;
    return Arrays.equals(data, other.data);
  }

  public int compareTo(Data data) {
    byte[] data1 = getRowData();
    byte[] data2 = data.getRowData();
    return new String(data1).compareTo(new String(data2));
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("Data(hex=");
    buffer.append(Hex.encodeHex(data));
    if (data.length == 8) {
      buffer.append(",long=" + asLong().toString());
    }
    buffer.append(",str=" + stringValue());
    buffer.append(")");
    return buffer.toString();
  }

  public String stringValue() {
    return new String(data);
  }

  public Long asLong() {
    return bytesToLong(data);
  }

  public Data addData(Data other) {
    return new Data(longToBytes(bytesToLong(data) + bytesToLong(other.data)));
  }
}
