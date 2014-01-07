package com.gromtable.server.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapUtil {
  private MapUtil() {
  }

  public static <Key, Value> List<Value> getValues(Map<Key, List<Value>> map) {
    List<Value> values = new ArrayList<Value>();

    for (Map.Entry<Key, List<Value>> entry : map.entrySet()) {
      values.addAll(entry.getValue());
    }

    return values;
  }
}
