package com.gromtable.server.api.logdata;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class LogDataController extends ApiController<LogDataRequest, LogDataResult> {
  public LogDataController createController() {
    return new LogDataController();
  }

  public String getPath() {
    return "/log_data";
  }

  protected LogDataRequest createControllerRequest(ApiRequest apiRequest) {
    return new LogDataRequest(apiRequest);
  }

  public String getDescription() {
    return "This api log data from client. Logging data is critical part for machine learning.";
  }

  protected LogDataResult genControllerResult(LogDataRequest request) {
    return new LogDataImpl(request.getCategory(), request.getData()).genLoad();
  }
}