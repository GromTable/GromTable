package com.gromtable.server.core.loader.base;

import junit.framework.Assert;

import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.data.Columns;
import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.RowKey;

public class EntityGetSetLoaderTest extends BaseTest {
  @Test
  public void saveLoadTest() {
    Id id = Id.genIdForDb(1, 2, 3);
    Columns columns = new Columns();
    columns.put(new RowKey("a"), new Data(new byte[] {'b'}));
    new EntityUpdateLoader(id, columns).genLoad();
    Columns loadedColumns = new EntityGetLoader(id).genLoad();
    Assert.assertEquals(columns, loadedColumns);
  }
}
