package com.gromtable.server.core.environment;

import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

import com.gromtable.server.core.data.log.Log4jDataLog;
import com.gromtable.server.core.loader.callback.HbaseStoreResource;
import com.gromtable.server.core.time.SystemTime;
import com.gromtable.server.fbapi.HttpFetcher;
import com.gromtable.server.settings.Settings;

public class ProductionEnvironment extends BaseEnvironment {
  private static final String HBASE_TABLE_NAME = "server_hbase_table_name";
  private static final String HBASE_FAMILY_NAME = "server_hbase_family_name";
  public ProductionEnvironment(Settings settings) {
    String hbaseTableName = settings.getServerSettings().get(HBASE_TABLE_NAME);
    String hbaseFamilyName = settings.getServerSettings().get(HBASE_FAMILY_NAME);
    Configuration config = HBaseConfiguration.create();
    config.set("hbase.master", "localhost:60000");
    setStore(new HbaseStoreResource(config, hbaseTableName, hbaseFamilyName));
    setRandom(new Random());
    setHttpFetcher(new HttpFetcher());
    setDataLog(new Log4jDataLog());
    setSettings(settings);
    setTime(new SystemTime());
  }

}
