package com.gromtable.server.core.data;

import com.gromtable.server.core.entity.EntityObject;

public class Key {
  public static final Key EMPTY = new Key("");
  private String str;

  public Key(String str) {
    this.str = str;
  }

  public Key(byte[] bytes) {
    this.str = EntityObject.bytesToString(bytes);
  }

  public boolean equals(Object other) {
    return str.equals(other.toString());
  }

  public int hashCode() {
    return str.hashCode();
  }

  public String toString() {
    return str;
  }

  public byte[] getBytes() {
    return EntityObject.stringToBytes(str);
  }

  public String getString() {
    return str;
  }
}
