package com.gromtable.server.core.entity;

public enum DocumentStatus {
  VOTING("VOTING"),
  ACCEPTED("ACCEPTED"),
  REJECTED("REJECTED"),
  CANCELED("CANCELED"),
  COUNTING("COUNTING");

  private final String name;

  DocumentStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
