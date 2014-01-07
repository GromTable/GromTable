package com.gromtable.server.core.loader.callback;

public class MemoryStoreResourceTest extends StoreResourceTest {
  public StoreResource getStoreResource() {
    return new MemoryStoreResource();
  }
}
