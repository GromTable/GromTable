package com.gromtable.server.core.entity;

public enum DocumentStatus {
  VOTING("VOTING"),
  ACCEPTED("ACCEPTED"),
  REJECTED("REJECTED"),
  CANCELED("CANCELED");

  private final String name;

  DocumentStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
