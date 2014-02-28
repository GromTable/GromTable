package com.gromtable.server.api.logout;

import com.gromtable.server.core.loader.Loader;

public class LogoutImpl extends Loader<LogoutResult> {
  public LogoutImpl() {
  }

  public LogoutResult genLoad() {
    return new LogoutResult();
  }
}
