package com.gromtable.server.api;

public class Assert {
  private Assert() {
  }

  public static void assertTrue(boolean condition, String message) {
    if (!condition) {
      throw new RuntimeException(message);
    }
  }

  public static void assertNull(Object object, String message) {
    if (object != null) {
      throw new RuntimeException(message);
    }
  }

  public static void fail(String message) {
    throw new RuntimeException(message);
  }
}
