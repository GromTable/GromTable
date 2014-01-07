package com.gromtable.server.api.logdata;

import com.gromtable.server.core.data.log.DataLog;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.loader.Loader;

public class LogDataImpl extends Loader<LogDataResult> {
  private String category;
  private Object data;

  public LogDataImpl(String category, Object data) {
    this.category = category;
    this.data = data;
  }

  public LogDataResult genLoad() {
    DataLog dataLog = BaseEnvironment.getEnvironment().getDataLog();

    dataLog.write(category, data);

    return new LogDataResult();
  }
}
