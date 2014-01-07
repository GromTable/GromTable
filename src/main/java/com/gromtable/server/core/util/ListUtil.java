package com.gromtable.server.core.util;

import com.gromtable.server.core.data.Id;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtil {
  private ListUtil() {
  }

  public static List<Long> getUnique(List<Long> ids) {
    Long[] idsArray = ids.toArray(new Long[ids.size()]);
    Arrays.sort(idsArray);

    List<Long> uniqueList = new ArrayList<Long>();

    for (long id : idsArray) {
      if (uniqueList.isEmpty() || (id != uniqueList.get(uniqueList.size() - 1))) {
        uniqueList.add(id);
      }
    }

    return uniqueList;
  }

  public static List<Id> getUniqueIds(List<Id> ids) {
    Id[] idsArray = ids.toArray(new Id[ids.size()]);
    Arrays.sort(idsArray);

    List<Id> uniqueList = new ArrayList<Id>();

    for (Id id : idsArray) {
      if (uniqueList.isEmpty() || (!id.equals(uniqueList.get(uniqueList.size() - 1)))) {
        uniqueList.add(id);
      }
    }

    return uniqueList;
  }
}
