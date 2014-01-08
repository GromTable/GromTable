package com.gromtable.server.api.getdocumentinfo;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.data.Id;

public class GetDocumentRequest extends BaseControllerRequest {
  private static final String DOCUMENT_ID = "document_id";
  private static final String SHOW_VOTES = "show_votes";
  private static final String VOTES_TIME = "votes_time";

  public GetDocumentRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      DOCUMENT_ID,
      RequestField.Type.ID,
      RequestField.Optionality.REQUIRED,
      "Document id."));
    requestFields.add(new RequestField(
      SHOW_VOTES,
      RequestField.Type.BOOLEAN,
      RequestField.Optionality.REQUIRED,
      "Should return votes information."));
    requestFields.add(new RequestField(
      VOTES_TIME,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "Time at which vote information to return."));
    return requestFields;
  }

  public Id getDocumentId() {
    return getId(DOCUMENT_ID);
  }

  public long getTime() {
    return Long.parseLong(getString(VOTES_TIME));
  }

  public boolean getShowVotes() {
    return getBoolean(SHOW_VOTES);
  }
}