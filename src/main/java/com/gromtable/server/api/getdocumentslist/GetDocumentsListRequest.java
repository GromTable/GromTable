package com.gromtable.server.api.getdocumentslist;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.data.Id;

public class GetDocumentsListRequest extends BaseControllerRequest {
  private static final String PARENT_ID = "parent_id";
  public GetDocumentsListRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      PARENT_ID,
      RequestField.Type.ID,
      RequestField.Optionality.OPTIONAL,
      "Parent document id."));
    return requestFields;
  }

  public Id getParentId() {
    return getId(PARENT_ID);
  }
}