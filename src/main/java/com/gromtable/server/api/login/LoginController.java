package com.gromtable.server.api.login;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class LoginController extends ApiController<LoginRequest, LoginResult> {

  public String getPath() {
    return "/login";
  }

  public LoginController createController() {
    return new LoginController();
  }

  protected boolean getRequireLoggedIn() {
    return false;
  }

  public String getDescription() {
    return "Return information about application";
  }

  protected LoginRequest createControllerRequest(ApiRequest apiRequest) {
    return new LoginRequest(apiRequest);
  }

  protected LoginResult genControllerResult(LoginRequest request) {
    return new LoginImpl(request.getCode(), request.getState()).genLoad();
  }
}
