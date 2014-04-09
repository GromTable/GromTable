package com.gromtable.server.api.createdocument;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.core.environment.BaseEnvironment;

public class CreateDocumentController extends ApiController<CreateDocumentRequest, CreateDocumentResult> {
  public String getPath() {
    return "/create_document";
  }

  public String getDescription() {
    return "Create document.";
  }

  public CreateDocumentController createController() {
    return new CreateDocumentController();
  }

  protected CreateDocumentRequest createControllerRequest(ApiRequest apiRequest) {
    return new CreateDocumentRequest(apiRequest);
  }

  protected CreateDocumentResult genControllerResult(CreateDocumentRequest request) {
    long voteByTime = BaseEnvironment.getEnvironment().getTime().getTimeMillis() + 48 * 60 * 60 * 1000L;
    return new CreateDocumentImpl(
      request.getParentId(), request.getName(),
      request.getText(), voteByTime, request.getViewerContext().getUserId()).genLoad();
  }
}
