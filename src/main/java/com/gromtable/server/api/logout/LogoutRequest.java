package com.gromtable.server.api.logout;

import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;

public class LogoutRequest extends BaseControllerRequest {
  public LogoutRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    return requestFields;
  }
}
