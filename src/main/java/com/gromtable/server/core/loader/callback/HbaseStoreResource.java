package com.gromtable.server.core.loader.callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Row;

import com.gromtable.server.core.loader.base.StoreLoader;

/**
 * Not thread safe because HTable is not thread safe.
 */
public class HbaseStoreResource extends StoreResource {
  private final Configuration configuration;
  private final byte[] tableName;
  private final byte[] familyName;
  private HTable table = null;

  public HbaseStoreResource(Configuration configuration, String tableName, String familyName) {
    this.configuration = configuration;
    this.tableName = tableName.getBytes();
    this.familyName = familyName.getBytes();
  }

  private HTable getTable() throws IOException {
    if (table == null) {
      table = new HTable(configuration, tableName);
    }
    return table;
  }

  public void dispatch() {
    try {
      HTable dataTable = getTable();
      List<StoreLoader<?>> loaders = popLoaders();
      List<Row> rows = new ArrayList<Row>();
      List<StoreLoader<?>> rowLoaders = new ArrayList<StoreLoader<?>>();
      List<Increment> increments = new ArrayList<Increment>();
      List<StoreLoader<?>> incrementLoaders = new ArrayList<StoreLoader<?>>();

      for (StoreLoader<?> loader : loaders) {
        loader.hbasePreDispatch(rows, rowLoaders, increments, incrementLoaders, familyName);
      }

      Object[] rowResults = null;
      List<Result> incrementResults = new ArrayList<Result>();
      try {
        rowResults = dataTable.batch(rows);
        for (Increment increment : increments) {
          incrementResults.add(dataTable.increment(increment));
        }
      } catch (InterruptedException e) {
        throw new IOException();
      }

      boolean wasError = false;
      for (int i = 0; i < rowLoaders.size(); i++) {
        Object rowResult = rowResults[i];
        StoreLoader<?> rowLoader = rowLoaders.get(i);
        if (rowResult instanceof Result) {
          Result result = (Result) rowResult;
          rowLoader.hbasePostDispatch(result, familyName);
        } else {
          wasError = true;
        }
      }

      for (int i = 0; i < incrementLoaders.size(); i++) {
        Result result = incrementResults.get(i);
        StoreLoader<?> incrementLoader = incrementLoaders.get(i);
        if (result instanceof Result) {
          incrementLoader.hbasePostDispatch(result, familyName);
        } else {
          wasError = true;
        }
      }

      if (wasError) {
        throw new IOException();
      }
    } catch (IOException exception) {
      throw new RuntimeException(exception.getMessage());
    }
  }
}
