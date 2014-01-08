package com.gromtable.server.core.entity;

public enum VoteUserDecision {
  DELEGATE("delegate"),
  RETRIEVE("retrieve");

  private final String name;

  VoteUserDecision(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
