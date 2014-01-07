package com.gromtable.server.core.environment;

import java.util.Random;

import com.gromtable.server.core.data.log.DataLog;
import com.gromtable.server.core.loader.callback.StoreResource;
import com.gromtable.server.fbapi.IHttpFetcher;
import com.gromtable.server.settings.Settings;

public class TestEnvironment extends BaseEnvironment {
  public void setStore(StoreResource store) {
    super.setStore(store);
  }

  public void setRandom(Random random) {
    super.setRandom(random);
  }

  public void setDataLog(DataLog dataLog) {
    super.setDataLog(dataLog);
  }

  public void setHttpFetcher(IHttpFetcher httpFetcher) {
    super.setHttpFetcher(httpFetcher);
  }

  public void setSettings(Settings settings) {
    super.setSettings(settings);
  }
}