package com.gromtable.server.api.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.gromtable.server.core.entity.ErrorType;
import com.gromtable.server.core.environment.BaseEnvironment;

public class BaseControllerResult {
  private ErrorType error;

  public BaseControllerResult setError(ErrorType error) {
    this.error = error;
    return this;
  }

  public ErrorType getError() {
    return error;
  }

  public RequestFields getResultFields() {
    return new RequestFields();
  }

  protected void setContextType(HttpServletResponse response) {
    response.setContentType("text/javascript; charset=utf-8");
  }

  protected void setUpCookie(HttpServletResponse response) throws IOException {
    if (ErrorType.NOT_LOGGED_IN.equals(this.getError())) {
      Cookie cookie = new Cookie("s", "");
      cookie.setMaxAge(0);
      cookie.setPath("/");
      response.addCookie(cookie);
    }
  }

  protected void writeResult(PrintWriter writer, String callback) throws IOException {
    if (callback != null) {
      writer.append(callback);
      writer.append("(");
    }
    System.out.println(BaseEnvironment.getEnvironment().getGson().toJson(this));
    BaseEnvironment.getEnvironment().getGson().toJson(this, writer);

    if (callback != null) {
      writer.append(")");
    }
  }

  public void writeResponse(HttpServletResponse response, String callback) throws IOException {
    setContextType(response);
    setUpCookie(response);
    PrintWriter writer = response.getWriter();
    writeResult(writer, callback);
    writer.close();
  }
}
