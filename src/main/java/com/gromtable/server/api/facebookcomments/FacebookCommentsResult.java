package com.gromtable.server.api.facebookcomments;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.gromtable.server.api.core.BaseControllerResult;

public class FacebookCommentsResult extends BaseControllerResult {
  private final String html;

  public FacebookCommentsResult(String html) {
    this.html = html;
  }

  String getHtml() {
    return html;
  }

  protected void setContextType(HttpServletResponse response) {
    response.setContentType("text/html");
  }

  protected void writeResult(PrintWriter writer, String callback) throws IOException {
    writer.append(html);
  }
}
