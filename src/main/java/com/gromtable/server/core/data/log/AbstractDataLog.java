package com.gromtable.server.core.data.log;

import com.google.gson.Gson;

public abstract class AbstractDataLog implements DataLog {
  private Gson gson = new Gson();

  protected String getLine(String category, Object data) {
    return category + "\t" + gson.toJson(data);
  }
}
