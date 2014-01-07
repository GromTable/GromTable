package com.gromtable.server.core;

import com.gromtable.server.core.data.log.ArrayDataLog;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.TestEnvironment;
import com.gromtable.server.core.loader.callback.MemoryStoreResource;
import com.gromtable.server.core.loader.callback.StoreResource;

import java.util.Random;

public class Setup {

  private Setup() {
  }

  public static TestEnvironment setTestEnvironment() {
    StoreResource storeResource = new MemoryStoreResource();
    TestEnvironment environment = new TestEnvironment();
    environment.setStore(storeResource);
    environment.setRandom(new Random(777));
    environment.setDataLog(new ArrayDataLog());
    BaseEnvironment.setEnvironment(environment);
    return environment;
  }
}
