package com.gromtable.server.api.login;

public class FbLoginToken implements LoginToken {
  private String fbUserId;
  public FbLoginToken(String fbUserId) {
    this.fbUserId = fbUserId;
  }
  public String getToken() {
    return "fb_" + fbUserId;
  }
}
