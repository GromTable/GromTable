package com.gromtable.server.core.loader.base;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.data.Columns;
import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.Rows;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.Environment;
import com.gromtable.server.core.loader.callback.StoreResource;

public class EntityGetLoader extends StoreLoader<Columns> {
  private final Id id;

  public EntityGetLoader(Id entityId) {
    this.id = entityId;
  }

  public Key getRowKey() {
    return id.getKey();
  }

  public void hbasePreDispatch(List<Row> rows, List<StoreLoader<?>> rowLoaders, List<Increment> increments,
      List<StoreLoader<?>> incrementLoaders, byte[] familyName) {
    Get row = new Get(getRowKey().getBytes());
    row.addFamily(familyName);

    rows.add(row);
    rowLoaders.add(this);
  }

  public void hbasePostDispatch(Result result, byte[] familyName) {
    Map<byte[], byte[]> familyMap = result.getFamilyMap(familyName);
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

  public Columns genLoad() {
    Environment environment = BaseEnvironment.getEnvironment();
    StoreResource store = environment.getStore();
    store.preGet(this);
    store.dispatch();
    return getResult();
  }
}
