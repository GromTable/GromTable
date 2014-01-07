package com.gromtable.server.api.core;

import java.io.Writer;

import com.gromtable.server.core.environment.BaseEnvironment;

public class BaseControllerResult {
  private String error;

  public BaseControllerResult setError(String error) {
    this.error = error;
    return this;
  }

  public String getError() {
    return error;
  }

  public RequestFields getResultFields() {
    return new RequestFields();
  }

  public final void writeResponse(Writer writer) {
    System.out.println(BaseEnvironment.getEnvironment().getGson().toJson(this));
    BaseEnvironment.getEnvironment().getGson().toJson(this, writer);
  }
}
