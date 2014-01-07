package com.gromtable.server.api.settings;

import java.util.Map;

import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.loader.Loader;

public class SettingsImpl extends Loader<SettingsResult> {
  public SettingsImpl() {
  }

  public SettingsResult genLoad() {
    Map<String, String> settings = BaseEnvironment.getEnvironment().getSettings().getClientSettings();
    return new SettingsResult(settings);
  }
}
