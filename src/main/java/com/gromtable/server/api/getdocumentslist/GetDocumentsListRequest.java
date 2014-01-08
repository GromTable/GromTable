package com.gromtable.server.api.getdocumentslist;

import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;

public class GetDocumentsListRequest extends BaseControllerRequest {
  public GetDocumentsListRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    return requestFields;
  }
}