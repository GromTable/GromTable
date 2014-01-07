package com.gromtable.server.core.data;

import java.util.HashMap;


@SuppressWarnings("serial")
public class Columns extends HashMap<Key, Data> {
  public static final Columns ALL_COLUMNS = new Columns(true);

  private boolean isAll = false;

  public Columns(boolean isAll) {
    this.isAll = isAll;
  }

  public Columns(Key toId, Data data) {
    put(toId, data);
  }

  public Columns() {
  }

  public Columns(Key columnName) {
  }

  public boolean isAll() {
    return isAll;
  }
}
