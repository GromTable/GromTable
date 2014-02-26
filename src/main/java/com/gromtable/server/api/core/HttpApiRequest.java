package com.gromtable.server.api.core;

import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.gromtable.server.api.Assert;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.environment.Environment;

public class HttpApiRequest implements ApiRequest {
  private final HttpServletRequest request;
  private String sessionCookie = null;

  public HttpApiRequest(HttpServletRequest request) {
    this.request = request;
  }

  @SuppressWarnings("unchecked")
  public Set<String> getKeys() {
    return request.getParameterMap().keySet();
  }

  public String getString(String key) {
    return request.getParameter(key);
  }

  public Long getLong(String key) {
    String param = ((String[]) request.getParameterMap().get(key))[0];

    if (param == null) {
      return null;
    } else {
      return Long.parseLong(param);
    }
  }

  public Id getId(String key) {
    return Id.fromKey(getString(key));
  }

  public Boolean getBoolean(String key) {
    return Boolean.parseBoolean(getString(key));
  }

  public void setEnvironment(Environment environment) {
    Assert.fail("Can not change environment");
  }

  public String getSessionCookie() {
    if (sessionCookie == null) {
      sessionCookie = "";
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals(SESSION_COOKIE)) {
          sessionCookie = cookie.getValue();
        }
      }
    }
    return sessionCookie;
  }

  public String getReferer() {
    return request.getHeader("Referer");
  }
}
