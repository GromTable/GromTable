package com.gromtable.server.core.loader.base;

import java.util.List;

import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.Rows;

public class HashoutAddLoader extends StoreLoader<Void> {
  private final Key rowKey;
  private final Key columnKey;
  private final Data data;

  public HashoutAddLoader(Id hashoutId, Key fromKey, Key toKey, Data data) {
    this(new Key[] {fromKey, hashoutId.getKey()}, new Key[] {toKey}, data);
  }

  public HashoutAddLoader(Id hashoutId, Key[] fromKeys, Key toKey, Data data) {
    this(combineKeys(fromKeys, hashoutId.getKey()), new Key[] {toKey}, data);
  }

  public HashoutAddLoader(Id hashoutId, Key fromKey, Key[] toKeys, Data data) {
    this(new Key[] {fromKey, hashoutId.getKey()}, toKeys, data);
  }


  public HashoutAddLoader(Key[] fromKeys, Key[] toKeys, Data data) {
    this.rowKey = getKey(fromKeys);
    this.columnKey = getKey(toKeys);
    this.data = data;
  }

  private static Key[] combineKeys(Key[] fromKeys, Key hashoutId) {
    return null;
  }

  public Key getRowKey() {
    return rowKey;
  }

  public Key getColumnKey() {
    return columnKey;
  }

  public Data getData() {
    return data;
  }

  public void setResult(Rows rows) {
  }

  public void hbasePreDispatch(List<Row> rows, List<StoreLoader<?>> rowLoaders, List<Increment> increments,
      List<StoreLoader<?>> incrementLoaders, byte[] familyName) {
    Put row = new Put(getRowKey().getBytes());
    row.add(familyName, getColumnKey().getBytes(), getData().getBytes());

    rows.add(row);
    rowLoaders.add(this);
  }

  public void hbasePostDispatch(Result result, byte[] familyName) {
  }

  public void memoryDispatch(Rows rows) {
    rows.put(getRowKey(), getColumnKey(), getData());
  }
}