package com.gromtable.server.api.core;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

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
    result.writeResponse(response, callback);
  }
}
