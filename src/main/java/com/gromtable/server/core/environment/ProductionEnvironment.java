package com.gromtable.server.core.environment;

import java.io.File;
import java.util.Random;

import com.gromtable.server.core.data.log.Log4jDataLog;
import com.gromtable.server.core.loader.callback.FileStoreResource;
import com.gromtable.server.fbapi.HttpFetcher;
import com.gromtable.server.settings.Settings;

public class ProductionEnvironment extends BaseEnvironment {
  private static final String STORE_FILE_KEY = "server_store_file_name";
  public ProductionEnvironment(Settings settings) {
    String storeFileName = settings.getServerSettings().get(STORE_FILE_KEY);
    setStore(new FileStoreResource(new File(storeFileName), this));
    setRandom(new Random());
    setHttpFetcher(new HttpFetcher());
    setDataLog(new Log4jDataLog());
    setSettings(settings);
  }

}
