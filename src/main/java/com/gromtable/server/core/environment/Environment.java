package com.gromtable.server.core.environment;

import java.util.Random;

import com.google.gson.Gson;
import com.gromtable.server.core.data.log.DataLog;
import com.gromtable.server.core.loader.callback.StoreResource;
import com.gromtable.server.core.time.Time;
import com.gromtable.server.fbapi.IHttpFetcher;
import com.gromtable.server.settings.Settings;

public interface Environment {
  IHttpFetcher getHttpFetcher();

  Gson getGson();

  DataLog getDataLog();

  Random getRandom();

  StoreResource getStore();

  Settings getSettings();

  Time getTime();
}
