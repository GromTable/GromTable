package com.gromtable.server.api.core;

import java.util.HashMap;

import com.gromtable.server.api.RequestField;

public class RequestFields extends HashMap<String, RequestField> {
  private static final long serialVersionUID = 1L;

  public void add(RequestField requestField) {
    RequestField wasBefore = put(requestField.getName(), requestField);
    if (wasBefore != null) {
      throw new RuntimeException("Such field already exists");
    }
  }
}
