package com.gromtable.server.core.loader.callback;

import java.util.List;

import com.gromtable.server.core.data.Rows;
import com.gromtable.server.core.loader.base.StoreLoader;

/**
 * Not thread safe because HTable is not thread safe.
 */
public class MemoryStoreResource extends StoreResource {
  private Rows rows = new Rows();

  protected Rows getRows() {
    return rows;
  }

  protected void setRows(Rows rows) {
    this.rows = rows;
  }

  public void dispatch() {
    List<StoreLoader<?>> loaders = popLoaders();

    for (StoreLoader<?> loader : loaders) {
      loader.memoryDispatch(rows);
    }
  }

  public String toString() {
    return rows.toString();
  }
}
