package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityUser;

public class HashoutUserToDelegatedUser extends Hashout<EntityUser> {
  public HashoutUserToDelegatedUser() {
    super(HashoutType.HASHOUT_USER_TO_DELEGATED_USER, EntityUser.class);
  }
}
