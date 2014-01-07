package com.gromtable.server.core.loader.base;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.data.Columns;
import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.Rows;

public class EntityUpdateLoader extends StoreLoader<Void> {
  private final Id id;
  private final Columns columns;

  public EntityUpdateLoader(Id entityId, Columns columns) {
    this.id = entityId;
    this.columns = columns;
  }

  public Id getId() {
    return id;
  }

  public Columns getColumns() {
    return columns;
  }

  public void hbasePreDispatch(List<Row> rows, List<StoreLoader<?>> rowLoaders, List<Increment> increments,
      List<StoreLoader<?>> incrementLoaders, byte[] familyName) {
    Put entityPutRow = new Put(getId().getRowData());
    for (Map.Entry<Key, Data> column : getColumns().entrySet()) {
      entityPutRow.add(familyName, column.getKey().getRowData(), column.getValue().getRowData());
    }

    rows.add(entityPutRow);
    rowLoaders.add(this);
  }

  public void hbasePostDispatch(Result result, byte[] familyName) {
  }

  public void memoryDispatch(Rows rows) {
    rows.put(getId(), getColumns());
  }
}
