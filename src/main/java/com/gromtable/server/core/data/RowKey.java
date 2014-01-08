package com.gromtable.server.core.data;

public class RowKey extends Key {
  public static final RowKey EMPTY = new RowKey(new byte[0]);
  private final byte[] data;

  public RowKey(byte[] data) {
    this.data = data;
  }

  public RowKey(String data) {
    this.data = data.getBytes();
  }

  public byte[] getRowData() {
    return data;
  }
}
