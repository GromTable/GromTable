package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityUserSession;

public class HashoutTokenToUserSession extends Hashout<EntityUserSession> {
  public HashoutTokenToUserSession() {
    super(HashoutType.HASHOUT_TOKEN_TO_USER_SESSION, EntityUserSession.class);
  }
}
