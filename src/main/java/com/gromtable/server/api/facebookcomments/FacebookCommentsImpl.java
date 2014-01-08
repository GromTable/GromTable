package com.gromtable.server.api.facebookcomments;

import com.gromtable.server.core.loader.Loader;

public class FacebookCommentsImpl extends Loader<FacebookCommentsResult> {
  private String url;

  public FacebookCommentsImpl(String url) {
    this.url = url;
  }

  public FacebookCommentsResult genLoad() {
    String html = ""
      + "<!DOCTYPE html>\n"
      + "<html>\n"
      + "  <head>\n"
      + "  </head>\n"
      + "  <body>\n"
      + "    <div id=\"fb-root\"></div>\n"
      + "    <script>(function(d, s, id) {\n"
      + "    var js, fjs = d.getElementsByTagName(s)[0];\n"
      + "    if (d.getElementById(id)) return;\n"
      + "    js = d.createElement(s); js.id = id;\n"
      + "    js.src = \"//connect.facebook.net/en_US/all.js#xfbml=1&appId=220468611468615\";\n"
      + "    fjs.parentNode.insertBefore(js, fjs);\n"
      + "    }(document, 'script', 'facebook-jssdk'));</script>\n"
      + "    <div class=\"fb-comments\" data-href=\""
      + url + "\" data-numposts=\"5\" data-colorscheme=\"light\"></div>\n"
      + "  </body>\n"
      + "</html>";
    return new FacebookCommentsResult(html);
  }
}
