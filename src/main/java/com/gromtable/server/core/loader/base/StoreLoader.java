package com.gromtable.server.core.loader.base;

import java.util.List;

import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.data.Rows;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.Environment;
import com.gromtable.server.core.loader.Loader;
import com.gromtable.server.core.loader.callback.StoreResource;

public abstract class StoreLoader<T> extends Loader<T> {
  private T result = null;

  protected static byte[] getBytes(String data) {
    return data.getBytes();
  }


  public void setResult(T result) {
    this.result = result;
  }

  public T getResult() {
    return result;
  }

  public abstract void memoryDispatch(Rows rows);

  public abstract void hbasePreDispatch(
      List<Row> rows, List<StoreLoader<?>> rowLoaders, List<Increment> increments,
      List<StoreLoader<?>> incrementLoaders, byte[] familyName);

  public abstract void hbasePostDispatch(Result result, byte[] familyName);

  public T genLoad() {
    Environment environment = BaseEnvironment.getEnvironment();
    StoreResource store = environment.getStore();
    store.preGet(this);
    store.dispatch();
    return getResult();
  }
}
