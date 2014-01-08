package com.gromtable.server.api.settings;

import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;

public class SettingsRequest extends BaseControllerRequest {
  public SettingsRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    return new RequestFields();
  }
}
