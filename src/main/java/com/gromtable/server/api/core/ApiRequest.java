package com.gromtable.server.api.core;

import java.util.Set;

import com.gromtable.server.core.data.Id;

public interface ApiRequest {
  public static final String SESSION_COOKIE = "s";

  public Set<String> getKeys();

  public String getString(String key);

  public Long getLong(String key);

  public Id getId(String key);

  public Boolean getBoolean(String key);

  public String getSessionCookie();

  public String getReferer();
}
