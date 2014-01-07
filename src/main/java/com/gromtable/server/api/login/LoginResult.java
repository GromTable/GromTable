package com.gromtable.server.api.login;

import com.gromtable.server.api.core.BaseControllerResult;

public class LoginResult extends BaseControllerResult {
  private final String sessionCookie;
  private final String redirectUrl;

  public LoginResult(String cookie, String redirectUrl) {
    this.sessionCookie = cookie;
    this.redirectUrl = redirectUrl;
  }
  public String getCookie() {
    return sessionCookie;
  }
  public String getRedirectUrl() {
    return redirectUrl;
  }
}
