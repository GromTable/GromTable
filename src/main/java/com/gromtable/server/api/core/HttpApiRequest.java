package com.gromtable.server.api.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.gromtable.server.api.Assert;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityObject;
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
    String str = request.getParameter(key);
    if (str == null) {
      return null;
    }
    try {
      str = URLDecoder.decode(str, EntityObject.ENCODING);
    } catch (UnsupportedEncodingException e) {
      return null;
    }
    return str;
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
    String keyId = getString(key);
    if (keyId != null) {
      return Id.fromKey(getString(key));
    }
    return null;
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
