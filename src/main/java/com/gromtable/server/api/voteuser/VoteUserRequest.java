package com.gromtable.server.api.voteuser;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.VoteUserDecision;

public class VoteUserRequest extends BaseControllerRequest {
  private static final String DELEGATE_ID = "delegate_id";
  private static final String VOTE_DECISION = "vote_decision";

  public VoteUserRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      DELEGATE_ID,
      RequestField.Type.ID,
      RequestField.Optionality.REQUIRED,
      "Choice id of the vote."));
    requestFields.add(new RequestField(
      VOTE_DECISION,
      RequestField.Type.STRING,
      RequestField.Optionality.REQUIRED,
      "Vote decision."));
    return requestFields;
  }

  public Id getDelegateId() {
    return getId(DELEGATE_ID);
  }

  public VoteUserDecision getVoteDecision() {
    return VoteUserDecision.valueOf(getString(VOTE_DECISION).toUpperCase());
  }
}
