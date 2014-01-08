package com.gromtable.server.core.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EqualsUtil {
  private EqualsUtil() {
  }

  public static boolean equals(Object x, Object y) {
    if (x == null) {
      return y == null;
    }
    return x.equals(y);
  }

  private static <T> Set<T> createSet(List<T> list) {
    if (list == null) {
      return null;
    }
    Set<T> set = new HashSet<T>();
    for (T element : list) {
      set.add(element);
    }
    return set;
  }

  public static <T> boolean setEquals(List<T> list1, List<T> list2) {
    return equals(createSet(list1), createSet(list2));
  }
}
