package com.gromtable.server.api.login;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.environment.TestEnvironment;
import com.gromtable.server.core.viewer.ViewerContext;
import com.gromtable.server.fbapi.IHttpFetcher;
import com.gromtable.server.fbapi.TestHttpFetcher;
import com.gromtable.server.settings.Settings;

public class LoginImplTest extends BaseTest {
  private IHttpFetcher getHttpFetcher() {
    Map<String, Object> requests = new HashMap<String, Object>();
    requests.put(
      "code=code",
      "access_token=accessToken&expires=100"
    );
    requests.put(
      "code=code1",
      "access_token=accessToken1&expires=100"
    );
    requests.put(
      "code=code2",
      "access_token=accessToken2&expires=100"
    );
    requests.put(
      "access_token=accessToken",
      "{\"id\": \"id\",\"name\": \"User\"}"
    );
    requests.put(
      "access_token=accessToken1",
      "{\"id\": \"id1\",\"name\": \"User1\"}"
    );
    requests.put(
      "access_token=accessToken2",
      "{\"id\": \"id2\",\"name\": \"User2\"}"
    );
    IHttpFetcher httpFetcher = new TestHttpFetcher(requests);
    return httpFetcher;
  }

  private Settings getSettings() {
    Map<String, String> serverSettings = new HashMap<String, String>();
    serverSettings.put(LoginImpl.CLIENT_ID_KEY, "client_id");
    serverSettings.put(LoginImpl.CLIENT_SECRET_KEY, "client_secret");
    Settings settings = new Settings(null, serverSettings);
    return settings;
  }

  private ViewerContext genLogin(final String code, final String state) {
    LoginResult loginResult = new LoginImpl(LoginImpl.FACEBOOK, code, state).genLoad();
    return ViewerContext.genCreateFromCookie(loginResult.getCookie());
  }

  @Test
  public void testNewUser() {
    TestEnvironment testEnvironmet = getTestEnvironment();
    testEnvironmet.setHttpFetcher(getHttpFetcher());
    testEnvironmet.setSettings(getSettings());

    ViewerContext viewerContext = genLogin("code", "url1DELIMITERurl2");
    Assert.assertTrue(viewerContext.isLoggedIn());
    Assert.assertEquals("User", viewerContext.genUser().getName());
  }

  @Test
  public void testSameUser() {
    TestEnvironment testEnvironmet = getTestEnvironment();
    testEnvironmet.setHttpFetcher(getHttpFetcher());
    testEnvironmet.setSettings(getSettings());

    Id firstUserId = genLogin("code", "url1DELIMITERurl2").getUserId();
    Id secondUserId = genLogin("code", "url1DELIMITERurl2").getUserId();
    Assert.assertEquals(firstUserId, secondUserId);
  }

  @Test
  public void testDifferentUser() {
    TestEnvironment testEnvironmet = getTestEnvironment();
    testEnvironmet.setHttpFetcher(getHttpFetcher());
    testEnvironmet.setSettings(getSettings());

    Id firstUserId = genLogin("code1", "url1DELIMITERurl2").getUserId();
    Id secondUserId = genLogin("code2", "url1DELIMITERurl2").getUserId();
    Assert.assertNotNull(firstUserId);
    Assert.assertNotNull(secondUserId);
    Assert.assertFalse(firstUserId.equals(secondUserId));
  }
}