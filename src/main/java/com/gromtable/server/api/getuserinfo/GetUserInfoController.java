package com.gromtable.server.api.getuserinfo;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class GetUserInfoController extends ApiController<GetUserInfoRequest, GetUserInfoResult> {
  public String getPath() {
    return "/get_user_info";
  }

  public String getDescription() {
    return "Get user info.";
  }

  protected boolean getRequireLoggedIn() {
    return false;
  }

  public GetUserInfoController createController() {
    return new GetUserInfoController();
  }

  protected GetUserInfoRequest createControllerRequest(ApiRequest apiRequest) {
    return new GetUserInfoRequest(apiRequest);
  }

  protected GetUserInfoResult genControllerResult(GetUserInfoRequest request) {
    return new GetUserInfoImpl(request.getUserId(), request.getShowVotes(), request.getTime()).genLoad();
  }
}
