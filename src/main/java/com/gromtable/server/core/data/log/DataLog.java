package com.gromtable.server.core.data.log;

public interface DataLog {

  public void write(String category, Object data);
}
