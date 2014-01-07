package com.gromtable.server.api.logdata;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.data.log.ArrayDataLog;
import com.gromtable.server.core.log.ArrayDataLogTest;

public class LogDataImplTest extends BaseTest {
  @Test
  public void testOneListener() {
    ArrayDataLog dataLog = (ArrayDataLog) getTestEnvironment().getDataLog();

    new LogDataImpl(ArrayDataLogTest.getCategory1(), ArrayDataLogTest.getData1()).genLoad();
    Assert.assertEquals(Arrays.asList(ArrayDataLogTest.getLine1()), dataLog.getLines());

    new LogDataImpl(ArrayDataLogTest.getCategory2(), ArrayDataLogTest.getData2()).genLoad();
    Assert.assertEquals(
      Arrays.asList(ArrayDataLogTest.getLine1(), ArrayDataLogTest.getLine2()), dataLog.getLines());
  }
}
