package com.gromtable.server.core.loader.base;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.data.Columns;
import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.Rows;

public class HashoutGetMultiLoader extends StoreLoader<Columns> {
  private final Key key;

  public HashoutGetMultiLoader(Key hashoutId, Key fromId) {
    this.key = getKey(fromId, hashoutId);
  }

  public Key getRowKey() {
    return key;
  }

  public void hbasePreDispatch(List<Row> rows, List<StoreLoader<?>> rowLoaders, List<Increment> increments,
      List<StoreLoader<?>> incrementLoaders, byte[] familyName) {
    Get row = new Get(getRowKey().getBytes());
    row.addFamily(familyName);

    rows.add(row);
    rowLoaders.add(this);
  }

  public void hbasePostDispatch(Result result, byte[] familyName) {
    System.out.println(result);
    System.out.println(familyName);
    Map<byte[], byte[]> familyMap = result.getFamilyMap(familyName);
    System.out.println(familyMap);
    Columns columns = new Columns();
    if (familyMap != null) {
      for (Map.Entry<byte[], byte[]> column : familyMap.entrySet()) {
        Key columnName = new Key(column.getKey());
        Data columnData = new Data(column.getValue());
        columns.put(columnName, columnData);
      }
    }
    setResult(columns);
  }

  public void memoryDispatch(Rows rows) {
    setResult(rows.get(getRowKey()));
  }
}
