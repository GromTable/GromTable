package com.gromtable.server.api.facebookcomments;

import java.io.IOException;
import java.io.Writer;

import com.gromtable.server.api.core.BaseControllerResult;

public class FacebookCommentsResult extends BaseControllerResult {
  private final String html;

  public FacebookCommentsResult(String html) {
    this.html = html;
  }

  String getHtml() {
    return html;
  }

  public void writeResponse(Writer writer) {
    try {
      writer.write(html);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
