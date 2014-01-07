package com.gromtable.server.api;

import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.core.data.Id;

public class RequestField {
  private static final int MINIMAL_DESCRIPTION_LENGTH = 10;
  public enum Type {
    BOOLEAN,
    STRING,
    ID
  }

  public enum Optionality {
     REQUIRED,
     OPTIONAL
  }

  private String name;
  private Type type;
  private Optionality optionality;
  private String description;

  public RequestField(String name, Type type, Optionality optionality, String description) {
    Assert.assertTrue(
      description.length() >= MINIMAL_DESCRIPTION_LENGTH,
      "Description for field is too small, please write longer description with examples.");
    this.name = name;
    this.type = type;
    this.optionality = optionality;
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public Optionality isOptional() {
    return optionality;
  }

  public Type getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  public Boolean getBoolean(ApiRequest apiRequest, String fieldName) {
    Assert.assertTrue(type == Type.BOOLEAN, "Request field suppose to be boolean");
    return apiRequest.getBoolean(fieldName);
  }

  public String getString(ApiRequest apiRequest, String fieldName) {
    Assert.assertTrue(type == Type.STRING, "Request field suppose to be string");
    return apiRequest.getString(fieldName);
  }

  public Id getId(ApiRequest apiRequest, String fieldName) {
    Assert.assertTrue(type == Type.ID, "Request field suppose to be Id");
    return apiRequest.getId(fieldName);
  }

  public boolean isValid(ApiRequest apiRequest) {
    if (isOptional() == Optionality.REQUIRED) {
      if (apiRequest.getString(getName()) == null) {
        return false;
      }
    }
    return true;
  }
}
