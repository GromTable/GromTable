package com.gromtable.server.api.facebookcomments;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;

public class FacebookCommentsRequest extends BaseControllerRequest {
  private static final String URL = "url";

  public FacebookCommentsRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      URL,
      RequestField.Type.STRING,
      RequestField.Optionality.REQUIRED,
      "URL for the comments."));
    return requestFields;
  }

  public String getUrl() {
    return getString(URL);
  }
}