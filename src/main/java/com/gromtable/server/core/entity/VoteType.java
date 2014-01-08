package com.gromtable.server.core.entity;

public enum VoteType {
  USER("user"),
  DOCUMENT("document");

  private final String name;

  VoteType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
