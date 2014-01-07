package com.gromtable.server.core.data.log;

import java.util.ArrayList;
import java.util.List;

public class ArrayDataLog extends AbstractDataLog {
  private List<String> lines = new ArrayList<String>();

  public void write(String category, Object data) {
    lines.add(getLine(category, data));
  }

  public List<String> getLines() {
    return lines;
  }
}
