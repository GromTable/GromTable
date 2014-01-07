package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityUser;

public class HashoutLoginTokenToUserTest extends HashoutTest<EntityUser> {
  protected HashoutLoginTokenToUser getHashout() {
    return new HashoutLoginTokenToUser();
  }

  protected EntityUser getEntityObject() {
    return new EntityUser("1", "Test User");
  }

}
