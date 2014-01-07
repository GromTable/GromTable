package com.gromtable.server.api.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.DecoderException;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.environment.Environment;

public class MapApiRequest implements ApiRequest {
  private Environment environment;
  private Map<String, Object> map = null;
  private String sessionCookie = null;

  public MapApiRequest(Environment environment, Map<String, Object> map) {
    this.environment = environment;
    this.map = map;
  }

  /**
   * Creates a new MapApiRequest object.
   */
  public MapApiRequest(Object... args) {
    this.map = new HashMap<String, Object>();

    for (int i = 0; i < args.length; i += 2) {
      this.map.put((String) args[i], args[i + 1]);
    }
  }

  public Environment getEnvironment() {
    return environment;
  }

  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  public Set<String> getKeys() {
    return map.keySet();
  }

  public String getString(String key) {
    return (String) map.get(key);
  }

  public Long getLong(String key) {
    return (Long) map.get(key);
  }

  public Id getId(String key) {
    try {
      return Id.fromBase16(getString(key));
    } catch (DecoderException e) {
      return null;
    }
  }

  public Boolean getBoolean(String key) {
    return (Boolean) map.get(key);
  }

  public String toString() {
    return map.toString();
  }

  public String getSessionCookie() {
    return sessionCookie;
  }

  public void setSessionCookie(String sessionCookie) {
    this.sessionCookie = sessionCookie;
  }

  public String getReferer() {
    return null;
  }
}
