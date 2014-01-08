package com.gromtable.server.fbapi;

import java.util.HashMap;
import java.util.Map;

public class TestHttpFetcher implements IHttpFetcher {
  private Map<String, Object> requests = new HashMap<String, Object>();

  public TestHttpFetcher(Map<String, Object> requests) {
    this.requests = requests;
  }

  public String genContent(String httpUrl) {
    Object response = null;
    for (String key : requests.keySet()) {
      if (httpUrl.endsWith(key)) {
        response = requests.get(key);
        break;
      }
    }
    if (response instanceof String) {
      return (String) response;
    }
    throw (RuntimeException) response;
  }
}
