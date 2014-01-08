package com.gromtable.server.api.setuserinfo;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.entity.UserType;

public class SetUserInfoRequest extends BaseControllerRequest {
  private static final String USER_TYPE = "user_type";

  public SetUserInfoRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      USER_TYPE,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user type for user."));
    return requestFields;
  }

  public UserType getUserType() {
    return UserType.valueOf(getString(USER_TYPE).toUpperCase());
  }
}