package com.gromtable.server.api.getloggedinuser;

import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;

public class GetLoggedinUserRequest extends BaseControllerRequest {
  public GetLoggedinUserRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    return new RequestFields();
  }
}