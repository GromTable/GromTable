package com.gromtable.server.api.createdocument;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.data.Id;

public class CreateDocumentRequest extends BaseControllerRequest {
  private static final String PARENT_ID = "parent_id";
  private static final String NAME = "name";
  private static final String TEXT = "text";
  private static final String VOTE_BY_TIME = "voteByTime";

  public CreateDocumentRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      PARENT_ID,
      RequestField.Type.ID,
      RequestField.Optionality.OPTIONAL,
      "Parent document id."));
    requestFields.add(new RequestField(
      NAME,
      RequestField.Type.STRING,
      RequestField.Optionality.REQUIRED,
      "Name of the document."));
    requestFields.add(new RequestField(
      TEXT,
      RequestField.Type.STRING,
      RequestField.Optionality.REQUIRED,
      "Text of the document."));
    requestFields.add(new RequestField(
      VOTE_BY_TIME,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "Vote by time."));
    return requestFields;
  }

  public Id getParentId() {
    return getId(PARENT_ID);
  }

  public String getName() {
    return getString(NAME);
  }

  public String getText() {
    return getString(TEXT);
  }

  public long getVoteByTime() {
    return Long.getLong(getString(VOTE_BY_TIME));
  }
}