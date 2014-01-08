package com.gromtable.server.api.login;

public class TestLoginToken implements LoginToken {
  private String testUserId;

  public TestLoginToken(String testUserId) {
    this.testUserId = testUserId;
  }

  public String getToken() {
    return "test_" + testUserId;
  }
}
