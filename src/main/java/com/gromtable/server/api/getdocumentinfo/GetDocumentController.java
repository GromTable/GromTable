package com.gromtable.server.api.getdocumentinfo;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class GetDocumentController extends ApiController<GetDocumentRequest, GetDocumentInfoResult> {
  public String getPath() {
    return "/get_document_info";
  }

  public String getDescription() {
    return "Get document information.";
  }

  protected boolean getRequireLoggedIn() {
    return false;
  }

  public GetDocumentController createController() {
    return new GetDocumentController();
  }

  protected GetDocumentRequest createControllerRequest(ApiRequest apiRequest) {
    return new GetDocumentRequest(apiRequest);
  }

  protected GetDocumentInfoResult genControllerResult(GetDocumentRequest request) {
    return new GetDocumentInfoImpl(request.getDocumentId(), request.getShowVotes(), request.getTime()).genLoad();
  }
}
