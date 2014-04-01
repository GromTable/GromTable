package com.gromtable.server.core.entity;

public enum ActionType {
  USER_JOINED("user_joined"),
  CHANGE_USER_TYPE("change_user_type"),
  CREATE_DOCUMENT("create_document"),
  CHANGE_DOCUMENT("change_document"),
  VOTE_USER("vote_user"),
  VOTE_DOCUMENT("vote_document");

  private final String name;

  ActionType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
