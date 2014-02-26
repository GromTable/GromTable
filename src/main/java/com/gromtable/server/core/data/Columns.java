package com.gromtable.server.core.data;

import java.util.HashMap;


@SuppressWarnings("serial")
public class Columns extends HashMap<Key, Data> {
  public static final Columns ALL_COLUMNS = new Columns(true);

  private boolean isAll = false;

  public Columns(boolean isAll) {
    this.isAll = isAll;
  }

  public Columns(Key toKey, Data data) {
    put(toKey, data);
  }

  public Columns() {
  }

  public Columns(String columnName) {
  }

  public boolean isAll() {
    return isAll;
  }
}
