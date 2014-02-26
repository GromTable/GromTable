package com.gromtable.server.core.loader.base;

import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.Rows;

public class HashoutGetLoader extends StoreLoader<Data> {
  private final Key rowKey;
  private final Key columnKey;

  public HashoutGetLoader(Id hashoutId, Key fromId, Key toId) {
    this.rowKey = getKey(fromId, hashoutId.getKey());
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
    Get row = new Get(getRowKey().getBytes());
    row.addColumn(familyName, getColumnKey().getBytes());

    rows.add(row);
    rowLoaders.add(this);
  }

  public void hbasePostDispatch(Result result, byte[] familyName) {
    KeyValue column = result.getColumnLatest(familyName, getColumnKey().getBytes());
    Data columnData = new Data(column.getValue());
    setResult(columnData);
  }

  public void memoryDispatch(Rows rows) {
    setResult(rows.getColumn(getRowKey(), getColumnKey()));
  }
}
