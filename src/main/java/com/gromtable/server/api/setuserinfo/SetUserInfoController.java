package com.gromtable.server.api.setuserinfo;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class SetUserInfoController extends ApiController<SetUserInfoRequest, SetUserInfoResult> {
  public String getPath() {
    return "/set_user_info";
  }

  public String getDescription() {
    return "Set user info.";
  }

  public SetUserInfoController createController() {
    return new SetUserInfoController();
  }

  protected SetUserInfoRequest createControllerRequest(ApiRequest apiRequest) {
    return new SetUserInfoRequest(apiRequest);
  }

  protected SetUserInfoResult genControllerResult(SetUserInfoRequest request) {
    return new SetUserInfoImpl(request.getViewerContext().getUserId(), request.getUserType()).genLoad();
  }
}
