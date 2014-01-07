package com.gromtable.server.api.settings;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class SettingsController extends ApiController<SettingsRequest, SettingsResult> {
  public String getPath() {
    return "/settings";
  }

  public String getDescription() {
    return "Get delegate graph.";
  }

  public SettingsController createController() {
    return new SettingsController();
  }

  protected boolean getRequireLoggedIn() {
    return false;
  }

  protected SettingsRequest createControllerRequest(ApiRequest apiRequest) {
    return new SettingsRequest(apiRequest);
  }

  protected SettingsResult genControllerResult(SettingsRequest request) {
    return new SettingsImpl().genLoad();
  }
}
