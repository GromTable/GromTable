package com.gromtable.server.core.environment;

import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.log.DataLog;
import com.gromtable.server.core.json.IdDeserializer;
import com.gromtable.server.core.json.IdSerializer;
import com.gromtable.server.core.loader.callback.StoreResource;
import com.gromtable.server.fbapi.IHttpFetcher;
import com.gromtable.server.settings.Settings;

public class BaseEnvironment implements Environment {
  private static Environment environment = null;
  private StoreResource store = null;
  private Random random = null;
  private DataLog dataLog = null;
  private Gson gson = null;
  private IHttpFetcher httpFetcher = null;
  private Settings settings = null;

  public BaseEnvironment() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(Id.class, new IdSerializer());
    builder.registerTypeAdapter(Id.class, new IdDeserializer());
    gson = builder.create();
  }

  public static void setEnvironment(Environment environment) {
    BaseEnvironment.environment = environment;
  }

  public static Environment getEnvironment() {
    return environment;
  }

  protected void setStore(StoreResource store) {
    this.store = store;
  }

  public StoreResource getStore() {
    return store;
  }

  protected void setHttpFetcher(IHttpFetcher httpFetcher) {
    this.httpFetcher = httpFetcher;
  }

  public IHttpFetcher getHttpFetcher() {
    return httpFetcher;
  }

  protected void setRandom(Random random) {
    this.random = random;
  }

  public Random getRandom() {
    return random;
  }

  protected void setDataLog(DataLog dataLog) {
    this.dataLog = dataLog;
  }

  public DataLog getDataLog() {
    return dataLog;
  }

  protected void setGson(Gson gson) {
    this.gson = gson;
  }

  public Gson getGson() {
    return gson;
  }

  protected void setSettings(Settings settings) {
    this.settings = settings;
  }

  public Settings getSettings() {
    return settings;
  }
}
