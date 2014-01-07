package com.gromtable.server.core.loader.base;

import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.Rows;

public class CounterLoader extends StoreLoader<Long> {
  private final Key rowKey;
  private final Key columnKey;
  private final Data data;

  public CounterLoader(Key rowKey, Key columnKey, long amount) {
    this.rowKey = rowKey;
    this.columnKey = columnKey;
    this.data = new Data(amount);
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

  public void setResult(Data data) {
    setResult(data.asLong());
  }

  public void hbasePreDispatch(List<Row> rows, List<StoreLoader<?>> rowLoaders, List<Increment> increments,
      List<StoreLoader<?>> incrementLoaders, byte[] familyName) {
    Increment increment = new Increment(getRowKey().getRowData());
    increment.addColumn(familyName, getColumnKey().getRowData(), getData().asLong());

    increments.add(increment);
    incrementLoaders.add(this);
  }

  public void hbasePostDispatch(Result result, byte[] familyName) {
    KeyValue column = result.getColumnLatest(familyName, getColumnKey().getRowData());
    Data columnData = new Data(column.getValue());
    setResult(columnData.asLong());
  }

  public void memoryDispatch(Rows rows) {
    setResult(rows.incrementColumn(getRowKey(), getColumnKey(), getData()));
  }
}
