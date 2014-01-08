package com.gromtable.server.api.votedocument;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.VoteDocumentDecision;

public class VoteDocumentRequest extends BaseControllerRequest {
  private static final String DOCUMENT_ID = "document_id";
  private static final String VOTE_DECISION = "vote_decision";

  public VoteDocumentRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      DOCUMENT_ID,
      RequestField.Type.ID,
      RequestField.Optionality.REQUIRED,
      "We are voting for this document id."));
    requestFields.add(new RequestField(
      VOTE_DECISION,
      RequestField.Type.STRING,
      RequestField.Optionality.REQUIRED,
      "Vote decision."));
    return requestFields;
  }

  public Id getChoiceId() {
    return getId(DOCUMENT_ID);
  }

  public VoteDocumentDecision getVoteDecision() {
    return VoteDocumentDecision.valueOf(getString(VOTE_DECISION).toUpperCase());
  }
}
