package com.gromtable.server.api.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
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

  public void writeResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Note: This is needed only for development
    String referer = request.getHeader("referer");
    URI refererUri = null;
    try {
      refererUri = new URI(referer);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    if (refererUri != null && refererUri.getHost().equals("localhost")) {
      String url = "http://" + refererUri.getHost() + ":" + refererUri.getPort();
      response.addHeader("Access-Control-Allow-Origin", url);
      response.addHeader("Access-Control-Allow-Credentials", "true");
      response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
      response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }
    result.writeResponse(response, callback);
  }
}
