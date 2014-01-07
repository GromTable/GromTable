package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityUser;

public class HashoutLoginTokenToUser extends Hashout<EntityUser> {
  public HashoutLoginTokenToUser() {
    super(HashoutType.HASHOUT_LOGIN_TOKEN_TO_USER, EntityUser.class);
  }
}
