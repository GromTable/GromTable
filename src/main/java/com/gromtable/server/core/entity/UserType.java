package com.gromtable.server.core.entity;

public enum UserType {
  VOTER("voter"),
  DELEGATE("delegate");

  private final String name;

  UserType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
