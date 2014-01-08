package com.gromtable.server.api.login;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;

public class LoginRequest extends BaseControllerRequest {
  private static final String TYPE = "type";
  private static final String CODE = "code";
  private static final String STATE = "state";

  public LoginRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      TYPE,
      RequestField.Type.STRING,
      RequestField.Optionality.REQUIRED,
      "Type of login system."));
    requestFields.add(new RequestField(
      CODE,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "Code used for getting access token."));
    requestFields.add(new RequestField(
      STATE,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "Redirect url."));
    return requestFields;
  }

  public String getType() {
    return getString(TYPE);
  }

  public String getCode() {
    return getString(CODE);
  }

  public String getState() {
    return getString(STATE);
  }
}