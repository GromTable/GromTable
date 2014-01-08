package com.gromtable.server.core.time;

public class SystemTime implements Time {

  public long getNanoTime() {
    return System.nanoTime();
  }
}
