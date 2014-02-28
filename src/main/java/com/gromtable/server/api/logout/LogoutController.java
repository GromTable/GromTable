package com.gromtable.server.api.logout;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class LogoutController extends ApiController<LogoutRequest, LogoutResult> {
  public String getPath() {
    return "/logout";
  }

  public String getDescription() {
    return "Logout from site.";
  }

  public LogoutController createController() {
    return new LogoutController();
  }

  protected boolean getRequireLoggedIn() {
    return false;
  }

  protected LogoutRequest createControllerRequest(ApiRequest apiRequest) {
    return new LogoutRequest(apiRequest);
  }

  protected LogoutResult genControllerResult(LogoutRequest request) {
    return new LogoutImpl().genLoad();
  }
}
