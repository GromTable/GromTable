package com.gromtable.server.api.logout;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.gromtable.server.api.core.BaseControllerResult;

public class LogoutResult extends BaseControllerResult {
  public LogoutResult() {
  }

  protected void setUpCookie(HttpServletResponse response) throws IOException {
    Cookie cookie = new Cookie("s", "");
    cookie.setMaxAge(0);
    cookie.setPath("/");
    response.addCookie(cookie);
  }
}
