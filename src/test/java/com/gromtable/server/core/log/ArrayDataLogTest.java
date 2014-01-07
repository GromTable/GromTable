package com.gromtable.server.core.log;

import com.gromtable.server.core.data.log.ArrayDataLog;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayDataLogTest {

  public static String getCategory1() {
    return "category1";
  }

  public static String getCategory2() {
    return "category2";
  }

  public static Map<String, String> getData1() {
    Map<String, String> data = new HashMap<String, String>();
    data.put("key1", "value1");
    data.put("key2", "value2");

    return data;
  }

  public static Map<String, String> getData2() {
    Map<String, String> data = new HashMap<String, String>();
    data.put("key3", "value3");
    data.put("key4", "value4");

    return data;
  }

  public static String getLine1() {
    return "category1\t{\"key2\":\"value2\",\"key1\":\"value1\"}";
  }

  public static String getLine2() {
    return "category2\t{\"key4\":\"value4\",\"key3\":\"value3\"}";
  }

  @Test
  public void test() {
    ArrayDataLog log = new ArrayDataLog();
    log.write(getCategory1(), getData1());
    log.write(getCategory2(), getData2());

    List<String> expectedLines = Arrays.asList(getLine1(), getLine2());
    List<String> actualLines = log.getLines();
    Assert.assertEquals(expectedLines, actualLines);
  }
}
