package com.gromtable.server.core.entity;

public enum VoteDocumentDecision {
  YES("yes"),
  NO("no"),
  HOLD("hold");

  private final String name;

  VoteDocumentDecision(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
