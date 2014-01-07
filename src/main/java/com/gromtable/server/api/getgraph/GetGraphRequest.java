package com.gromtable.server.api.getgraph;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.data.Id;

public class GetGraphRequest extends BaseControllerRequest {
  private static final String USER_ID = "user_id";
  private static RequestFields requestFields = null;

  public GetGraphRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    if (requestFields == null) {
      requestFields = getBaseRequestFields();
      requestFields.add(new RequestField(
        USER_ID,
        RequestField.Type.ID,
        RequestField.Optionality.OPTIONAL,
        "User id for which graph will be generated."));
    }
    return requestFields;
  }

  public Id getUserId() {
    return getId(USER_ID);
  }
}
