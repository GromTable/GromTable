package com.gromtable.server.core.viewer;


import com.gromtable.server.core.data.Id;

/**
 * We are generating random numbers and encrypting in order
 * to prevent brute force attack.
 */
public class UserSessionToken {
  private Id id;

  public UserSessionToken(Id id) {
    this.id = id;
  }

  public Id getId() {
    return id;
  }

  // TODO: add additional security
  public static UserSessionToken createFromCookieToken(String cookie) {
    Id token = Id.fromKey(cookie);
    if (token == null) {
      return null;
    }
    return new UserSessionToken(token);
  }

  public String getCookieData() {
    return id.getString();
  }
}
