package com.gromtable.server.api.voteuser;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class VoteUserController extends ApiController<VoteUserRequest, VoteUserResult> {
  public String getPath() {
    return "/vote_user";
  }

  public String getDescription() {
    return "Vote for user.";
  }

  public VoteUserController createController() {
    return new VoteUserController();
  }

  protected VoteUserRequest createControllerRequest(ApiRequest apiRequest) {
    return new VoteUserRequest(apiRequest);
  }

  protected VoteUserResult genControllerResult(VoteUserRequest request) {
    return new VoteUserImpl(
      request.getViewerContext().getUserId(), request.getDelegateId(), request.getVoteDecision()).genLoad();
  }
}
