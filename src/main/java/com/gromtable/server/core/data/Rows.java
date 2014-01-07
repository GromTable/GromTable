package com.gromtable.server.core.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.hadoop.hbase.client.Result;


@SuppressWarnings("serial")
public class Rows extends HashMap<Key, Columns> {

  public void addResult(byte[] family, Result result) {
    Key key = new RowKey(result.getRow());
    Map<byte[], byte[]> familyMap = result.getFamilyMap(family);
    Columns columns = new Columns();
    for (Map.Entry<byte[], byte[]> column : familyMap.entrySet()) {
      Key columnName = new RowKey(column.getKey());
      Data columnData = new Data(column.getValue());
      columns.put(columnName, columnData);
    }
    put(key, columns);
  }

  public Data getColumn(Key key, Key column) {
    Columns columns = get(key);
    if (columns == null) {
      return null;
    }
    return columns.get(column);
  }

  public Columns getColumns(Key key) {
    return get(key);
  }

  public Columns putColumns(Key key, Columns columns) {
    for (Map.Entry<Key, Data> column : columns.entrySet()) {
      put(key, column.getKey(), column.getValue());
    }
    return columns;
  }

  public void put(Key key, Key column, Data data) {
    Columns columns = getColumns(key);
    if (columns == null) {
      columns = new Columns();
      put(key, columns);
    }
    columns.put(column, data);
  }

  public Data incrementColumn(Key rowKey, Key columnKey, Data data) {
    Data oldValue = getColumn(rowKey, columnKey);
    Data newValue = null;
    if (oldValue == null) {
      newValue = data;
    } else {
      newValue = oldValue.addData(data);
    }
    put(rowKey, columnKey, newValue);
    return newValue;
  }

  public void delete(Key key, Key column) {
    Columns columns = getColumns(key);
    if (columns != null) {
      columns.remove(column);
      if (columns.size() == 0) {
        remove(key);
      }
    }
  }

  public void delete(Key key, Columns columnsToDelete) {
    Columns columns = getColumns(key);
    if (columns != null) {
      for (Map.Entry<Key, Data> column : columnsToDelete.entrySet()) {
        columns.remove(column.getKey());
      }
      if (columns.size() == 0) {
        remove(key);
      }
    }
  }

  private static String hex(byte[] data) {
    return Hex.encodeHexString(data);
  }

  private static byte[] unhex(String hex) throws DecoderException {
    return Hex.decodeHex(hex.toCharArray());
  }

  public String serializeRow() {
    StringBuffer buffer = new StringBuffer();
    for (Map.Entry<Key, Columns> rowEntity : entrySet()) {
      for (Map.Entry<Key, Data> columnEntity: rowEntity.getValue().entrySet()) {
        buffer.append(hex(rowEntity.getKey().getRowData()));
        buffer.append(",");
        buffer.append(hex(columnEntity.getKey().getRowData()));
        buffer.append(",");
        buffer.append(hex(columnEntity.getValue().getRowData()));
        buffer.append(",");
      }
    }
    return buffer.toString();
  }

  public String serializePretty() {
    StringBuffer buffer = new StringBuffer();
    for (Map.Entry<Key, Columns> rowEntity : entrySet()) {
      buffer.append(rowEntity.getKey() + "\n");
      for (Map.Entry<Key, Data> columnEntity: rowEntity.getValue().entrySet()) {
        buffer.append("  ");
        buffer.append(columnEntity.getKey());
        buffer.append("=");
        buffer.append(columnEntity.getValue());
        buffer.append("\n");
      }
    }
    return buffer.toString();
  }

  public String toString() {
    return serializePretty();
  }

  public static Rows createFromString(String str) throws DecoderException {
    Rows rows = new Rows();
    str = str.trim();
    String[] parts = str.split(",");
    int index = 0;
    while (index + 2 < parts.length) {
      Key rowKey = new RowKey(unhex(parts[index++]));
      Key columnKey = new RowKey(unhex(parts[index++]));
      Data columnData = new Data(unhex(parts[index++]));
      rows.put(rowKey, columnKey, columnData);
    }
    return rows;
  }
}