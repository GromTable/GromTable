package com.gromtable.server.core.loader.base;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.RowKey;
import com.gromtable.server.core.data.Rows;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.Environment;
import com.gromtable.server.core.loader.Loader;
import com.gromtable.server.core.loader.callback.StoreResource;

public abstract class StoreLoader<T> extends Loader<T> {
  public static final byte DELIMITER = (byte) ':';
  private T result = null;

  protected static byte[] getBytes(String data) {
    return data.getBytes();
  }

  private static Key getKey(Key[] ids) {
    int capacity = 0;
    for (Key id : ids) {
      capacity += id.getRowData().length + 1;
    }
    ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
    for (Key id : ids) {
      byteBuffer.put(DELIMITER);
      byteBuffer.put(id.getRowData());
    }
    return new RowKey(byteBuffer.array());
  }

  protected static Key getKey(Key id1, Key id2) {
    return getKey(new Key[] {id1, id2});
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
