package com.gromtable.server.api.core;

public class Documentation {
  @SuppressWarnings("unused")
  private String description;
  @SuppressWarnings("unused")
  private RequestFields requestFields;
  @SuppressWarnings("unused")
  private RequestFields resultFields;

  public Documentation(String description, RequestFields requestFields, RequestFields resultFields) {
    this.description = description;
    this.requestFields = requestFields;
    this.resultFields = resultFields;
  }
}
