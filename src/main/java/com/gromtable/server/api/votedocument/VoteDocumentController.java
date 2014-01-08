package com.gromtable.server.api.votedocument;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class VoteDocumentController extends ApiController<VoteDocumentRequest, VoteDocumentResult> {
  public String getPath() {
    return "/vote_document";
  }

  public String getDescription() {
    return "Vote for document.";
  }

  public VoteDocumentController createController() {
    return new VoteDocumentController();
  }

  protected VoteDocumentRequest createControllerRequest(ApiRequest apiRequest) {
    return new VoteDocumentRequest(apiRequest);
  }

  protected VoteDocumentResult genControllerResult(VoteDocumentRequest request) {
    return new VoteDocumentImpl(
      request.getViewerContext().getUserId(), request.getChoiceId(), request.getVoteDecision()).genLoad();
  }
}
