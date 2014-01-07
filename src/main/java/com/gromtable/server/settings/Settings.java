package com.gromtable.server.settings;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;

public class Settings {
  private static final String CLIENT_SETTINGS_PREFIX = "client_";
  private static final String SERVER_SETTINGS_PREFIX = "server_";

  private final Map<String, String> clientSettings;
  private final Map<String, String> serverSettings;

  public Settings(Map<String, String> clientSettings, Map<String, String> serverSettings) {
    this.clientSettings = clientSettings;
    this.serverSettings = serverSettings;
  }

  public Settings(ServletConfig servletConfig) {
    @SuppressWarnings("unchecked")
    Enumeration<String> enumerator = (Enumeration<String>) servletConfig.getInitParameterNames();
    clientSettings = new HashMap<String, String>();
    serverSettings = new HashMap<String, String>();
    while (enumerator.hasMoreElements()) {
      String paramName = enumerator.nextElement();
      if (paramName.startsWith(CLIENT_SETTINGS_PREFIX)) {
        clientSettings.put(paramName, servletConfig.getInitParameter(paramName));
      }
      if (paramName.startsWith(SERVER_SETTINGS_PREFIX)) {
        serverSettings.put(paramName, servletConfig.getInitParameter(paramName));
      }
    }
  }

  public Map<String, String> getClientSettings() {
    return clientSettings;
  }

  public Map<String, String> getServerSettings() {
    return serverSettings;
  }
}
