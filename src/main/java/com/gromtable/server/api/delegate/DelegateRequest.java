package com.gromtable.server.api.delegate;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.data.Id;

public class DelegateRequest extends BaseControllerRequest {
  private static final String DELEGATE_TO = "delegate_to";
  private static RequestFields requestFields = null;

  public DelegateRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    if (requestFields == null) {
      requestFields = getBaseRequestFields();
      requestFields.add(new RequestField(
        DELEGATE_TO,
        RequestField.Type.ID,
        RequestField.Optionality.REQUIRED,
        "Type of login system used for this login."));
    }
    return requestFields;
  }

  public Id getDelegateTo() {
    return getId(DELEGATE_TO);
  }
}
