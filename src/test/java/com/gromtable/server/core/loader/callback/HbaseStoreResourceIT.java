package com.gromtable.server.core.loader.callback;

import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class HbaseStoreResourceIT extends StoreResourceTest {
  private static final String TABLE_FAMILY = "d";
  private static final String TABLE_NAME = "test";
  private static final HBaseTestingUtility TEST_UTIL = new HBaseTestingUtility();
  private StoreResource storeResource = null;

  private static void startCluster() throws Exception {
    TEST_UTIL.getConfiguration().addResource("hbase-site-local.xml");
    TEST_UTIL.getConfiguration().reloadConfiguration();
    // start mini hbase cluster
    TEST_UTIL.startMiniCluster(1);
  }

  private static void stopCluster() throws Exception {
    TEST_UTIL.shutdownMiniCluster();
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
    startCluster();
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    stopCluster();
  }

  @Before
  public void setUp() throws Exception {
    TEST_UTIL.createTable(TABLE_NAME.getBytes(), TABLE_FAMILY.getBytes());
    storeResource = new HbaseStoreResource(TEST_UTIL.getConfiguration(), TABLE_NAME,
        TABLE_FAMILY);
  }

  @After
  public void tearDown() throws Exception {
    TEST_UTIL.deleteTable(TABLE_NAME.getBytes());
  }

  @Override
  public StoreResource getStoreResource() {
    return storeResource;
  }
}
