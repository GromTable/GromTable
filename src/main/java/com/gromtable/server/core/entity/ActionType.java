package com.gromtable.server.core.entity;

public enum ActionType {
  VOTE_USER("vote_user"),
  VOTE_DOCUMENT("vote_document"),
  CREATE_DOCUMENT("create_document");

  private final String name;

  ActionType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
