package com.gromtable.server.fbapi;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpFetcher implements IHttpFetcher {
  public String genContent(final String httpUrl) {
    try {
      URL url = new URL(httpUrl);
      InputStream is = url.openConnection().getInputStream();

      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuffer buffer = new StringBuffer();

      String line = null;
      while ((line = reader.readLine()) != null)  {
        buffer.append(line);
      }
      reader.close();
      return buffer.toString();
    } catch (Exception e) {
      return null;
    }
  }
}