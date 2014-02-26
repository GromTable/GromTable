package com.gromtable.server.core.loader.base;

import java.util.List;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.Rows;

public class HashoutRemoveLoader extends StoreLoader<Void> {
  private final Key rowKey;
  private final Key columnKey;

  public HashoutRemoveLoader(Key hashoutId, Key fromId, Key toId) {
    this.rowKey = getKey(fromId, hashoutId);
    this.columnKey = toId;
  }

  public Key getRowKey() {
    return rowKey;
  }

  public Key getColumnKey() {
    return columnKey;
  }

  public void hbasePreDispatch(List<Row> rows, List<StoreLoader<?>> rowLoaders, List<Increment> increments,
      List<StoreLoader<?>> incrementLoaders, byte[] familyName) {
    Delete row = new Delete(getRowKey().getBytes());
    row.deleteColumn(familyName, getColumnKey().getBytes());

    rows.add(row);
    rowLoaders.add(this);
  }

  public void hbasePostDispatch(Result result, byte[] familyName) {
  }

  public void memoryDispatch(Rows rows) {
    rows.delete(getRowKey(), getColumnKey());
  }
}
