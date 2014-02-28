package com.gromtable.server.core.entity;

public enum ErrorType {
  NOT_LOGGED_IN("not_logged_in"),
  INVALID_REQUEST("invalid_request"),
  UKNOWN_ERROR("unknown_error");

  private final String name;

  ErrorType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
