package com.gromtable.server.api.gettranslationinfo;

import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;

public class GetTranslationInfoRequest extends BaseControllerRequest {
  public GetTranslationInfoRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    return requestFields;
  }
}
