package com.gromtable.server.core.hashout;

import com.gromtable.server.core.data.Key;

public class HashoutRecord {
  private Key key;

  private long time;

  /**
   * Creates a new HashoutEntry object.
   */
  public HashoutRecord() {
  }

  public HashoutRecord(Key key, long time) {
    this.setKey(key);
    this.setTime(time);
  }

  public Key getKey() {
    return key;
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }
}
