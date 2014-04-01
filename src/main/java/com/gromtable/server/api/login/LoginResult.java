package com.gromtable.server.api.login;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.gromtable.server.api.core.BaseControllerResult;

public class LoginResult extends BaseControllerResult {
  private final String sessionCookie;
  private final String redirectUrl;

  public LoginResult(String cookie, String redirectUrl) {
    this.sessionCookie = cookie;
    this.redirectUrl = redirectUrl;
  }

  protected void setUpCookie(HttpServletResponse response) throws IOException {
    if (this instanceof LoginResult && this.getError() == null) {
      LoginResult loginResult = (LoginResult) this;
      Cookie cookie = new Cookie("s", loginResult.getCookie());
      cookie.setMaxAge(30 * 24 * 60 * 60);
      cookie.setPath("/");
      response.addCookie(cookie);
      System.out.println(redirectUrl);
      response.sendRedirect(redirectUrl);
    }
  }

  public String getCookie() {
    return sessionCookie;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }
}
