package com.gromtable.server.core.time;

public class ConstantTime implements Time {
  private long nanoTime = 0;

  public long getTimeMillis() {
    return nanoTime;
  }

  public void setNanoTime(long nanoTime) {
    this.nanoTime = nanoTime;
  }
}
