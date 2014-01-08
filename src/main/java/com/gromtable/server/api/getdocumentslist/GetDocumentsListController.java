package com.gromtable.server.api.getdocumentslist;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class GetDocumentsListController extends ApiController<GetDocumentsListRequest, GetDocumentsListResult> {
  public String getPath() {
    return "/get_documents_list";
  }

  public String getDescription() {
    return "Return list of documents.";
  }

  protected boolean getRequireLoggedIn() {
    return false;
  }

  public GetDocumentsListController createController() {
    return new GetDocumentsListController();
  }

  protected GetDocumentsListRequest createControllerRequest(ApiRequest apiRequest) {
    return new GetDocumentsListRequest(apiRequest);
  }

  protected GetDocumentsListResult genControllerResult(GetDocumentsListRequest request) {
    return new GetDocumentsListImpl().genLoad();
  }
}
