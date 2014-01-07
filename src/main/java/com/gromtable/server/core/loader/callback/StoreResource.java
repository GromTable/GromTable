package com.gromtable.server.core.loader.callback;

import java.util.ArrayList;
import java.util.List;

import com.gromtable.server.core.loader.base.StoreLoader;

public abstract class StoreResource implements CallBackResource<StoreLoader<?>> {
  private List<StoreLoader<?>> loaders = new ArrayList<StoreLoader<?>>();

  public void preGet(StoreLoader<?> loader) {
    loaders.add(loader);
  }

  protected List<StoreLoader<?>> popLoaders() {
    List<StoreLoader<?>> popedLoaders = loaders;
    loaders = new ArrayList<StoreLoader<?>>();
    return popedLoaders;
  }
}
