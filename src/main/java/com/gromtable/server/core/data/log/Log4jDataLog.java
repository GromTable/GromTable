package com.gromtable.server.core.data.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jDataLog extends AbstractDataLog {
  private Logger logger = Logger.getLogger(Log4jDataLog.class);

  public void write(String category, Object data) {
    logger.log(Level.INFO, getLine(category, data));
  }
}
