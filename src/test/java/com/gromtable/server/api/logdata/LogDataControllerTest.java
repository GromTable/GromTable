package com.gromtable.server.api.logdata;

import com.google.gson.Gson;
import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.log.ArrayDataLogTest;
import com.gromtable.server.core.viewer.UserSessionToken;
import com.gromtable.server.core.viewer.ViewerContext;
import com.gromtable.server.api.core.ApiResult;
import com.gromtable.server.api.core.MapApiRequest;
import com.gromtable.server.core.data.log.ArrayDataLog;
import com.gromtable.server.core.entity.EntityUser;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LogDataControllerTest extends BaseTest {
  private MapApiRequest apiRequest = null;
  private LogDataResult logDataResult = null;

  public LogDataControllerTest(MapApiRequest apiRequest, LogDataResult logDataResult) {
    this.apiRequest = apiRequest;
    this.logDataResult = logDataResult;
  }

  @Test
  public void test() {
    UserSessionToken userSession = ViewerContext.genLogin(new EntityUser("1", "Viewer").save().getId());
    apiRequest.setSessionCookie(userSession.getCookieData());
    LogDataController logDataController = new LogDataController();
    logDataController.setRequest(apiRequest);

    ApiResult actualResult = logDataController.genResult();
    ApiResult expectedResult = new ApiResult(logDataResult);
    Gson gson = getTestEnvironment().getGson();
    Assert.assertEquals(gson.toJson(expectedResult), gson.toJson(actualResult));
    ArrayDataLog dataLog = (ArrayDataLog) getTestEnvironment().getDataLog();
    Assert.assertEquals(dataLog.getLines(), Arrays.asList(ArrayDataLogTest.getLine1()));
  }

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] {
      new Object[] {
        new MapApiRequest(
          LogDataRequest.CATEGORY_FIELD, ArrayDataLogTest.getCategory1(),
          LogDataRequest.DATA_FIELD, ArrayDataLogTest.getData1().toString()
        ),
        new LogDataResult()
      }
    });
  }
}