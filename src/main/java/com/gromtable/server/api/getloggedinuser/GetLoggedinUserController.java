package com.gromtable.server.api.getloggedinuser;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.core.data.Id;

public class GetLoggedinUserController extends ApiController<GetLoggedinUserRequest, GetLoggedinUserResult> {
  public String getPath() {
    return "/get_loggedin_user";
  }

  public String getDescription() {
    return "Return logged in user.";
  }

  public GetLoggedinUserController createController() {
    return new GetLoggedinUserController();
  }

  protected GetLoggedinUserRequest createControllerRequest(ApiRequest apiRequest) {
    return new GetLoggedinUserRequest(apiRequest);
  }

  protected GetLoggedinUserResult genControllerResult(GetLoggedinUserRequest request) {
    Id userId = request.getViewerContext().getUserId();
    return new GetLoggedinUserImpl(userId).genLoad();
  }
}
