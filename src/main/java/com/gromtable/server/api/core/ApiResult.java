package com.gromtable.server.api.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.gromtable.server.api.login.LoginResult;

public class ApiResult {
  private BaseControllerResult result;
  private String callback = null;;

  public ApiResult(BaseControllerResult result) {
    this.result = result;
  }

  public ApiResult(BaseControllerResult response, String callback) {
    this.result = response;
    this.callback = callback;
  }

  public void writeResponse(HttpServletResponse response) throws IOException {
    response.addHeader("Access-Control-Allow-Origin", "http://localhost:3030");
    response.addHeader("Access-Control-Allow-Credentials", "true");
    response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    response.setContentType("text/javascript");
    // TODO: clowntown
    // TODO: use constant instead of 's'
    if (result instanceof LoginResult && result.getError() == null) {
      LoginResult loginResult = (LoginResult) result;
      Cookie cookie = new Cookie("s", loginResult.getCookie());
      cookie.setMaxAge(30 * 24 * 60 * 60);
      cookie.setPath("/");
      response.addCookie(cookie);
      response.sendRedirect(loginResult.getRedirectUrl());
      return;
    }
    if (ApiController.NOT_LOGGED_IN.equals(result.getError())) {
      Cookie cookie = new Cookie("s", "");
      cookie.setMaxAge(0);
      cookie.setPath("/");
      response.addCookie(cookie);
    }

    PrintWriter writer = response.getWriter();
    if (callback != null) {
      writer.append(callback);
      writer.append("(");
    }
    result.writeResponse(writer);
    if (callback != null) {
      writer.append(")");
    }
    writer.close();
  }
}
