package com.gromtable.server.api.delegate;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class DelegateController extends ApiController<DelegateRequest, DelegateResult> {
  public String getPath() {
    return "/delegate";
  }

  public String getDescription() {
    return "Delegate vote.";
  }

  public DelegateController createController() {
    return new DelegateController();
  }

  protected DelegateRequest createControllerRequest(ApiRequest apiRequest) {
    return new DelegateRequest(apiRequest);
  }

  protected DelegateResult genControllerResult(DelegateRequest request) {
    return new DelegateImpl(request.getViewerContext().getUserId(), request.getDelegateTo()).genLoad();
  }
}
