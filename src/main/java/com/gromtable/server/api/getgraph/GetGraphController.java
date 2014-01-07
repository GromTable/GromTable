package com.gromtable.server.api.getgraph;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.core.data.Id;

public class GetGraphController extends ApiController<GetGraphRequest, GetGraphResult> {
  public String getPath() {
    return "/getgraph";
  }

  public String getDescription() {
    return "Get delegate graph.";
  }

  public GetGraphController createController() {
    return new GetGraphController();
  }

  protected GetGraphRequest createControllerRequest(ApiRequest apiRequest) {
    return new GetGraphRequest(apiRequest);
  }

  protected GetGraphResult genControllerResult(GetGraphRequest request) {
    Id userId = request.getUserId();
    if (userId == null) {
      userId = request.getViewerContext().getUserId();
    }
    return new GetGraphImpl(userId).genLoad();
  }
}
