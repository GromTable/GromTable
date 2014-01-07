package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityUser;

// TODO: We need support for 2 way hashout.
public class HashoutDelegatedUserToUser extends Hashout<EntityUser> {
  public HashoutDelegatedUserToUser() {
    super(HashoutType.HASHOUT_DELEGATED_USER_TO_USER, EntityUser.class);
  }
}
