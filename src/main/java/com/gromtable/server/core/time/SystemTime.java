package com.gromtable.server.core.time;

public class SystemTime implements Time {

  public long getTimeMillis() {
    return System.currentTimeMillis();
  }
}
