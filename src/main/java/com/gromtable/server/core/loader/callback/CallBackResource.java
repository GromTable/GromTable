package com.gromtable.server.core.loader.callback;

public interface CallBackResource<T> {

  public void preGet(T loader);

  public void dispatch();
}
