package com.gromtable.server.api.settings;

import java.util.Map;

import com.gromtable.server.api.core.BaseControllerResult;

public class SettingsResult extends BaseControllerResult {
  private final Map<String, String> settings;

  public SettingsResult(Map<String, String> settings) {
    this.settings = settings;
  }

  Map<String, String> getSettings() {
    return settings;
  }
}
