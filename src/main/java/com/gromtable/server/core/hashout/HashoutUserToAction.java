package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityAction;

public class HashoutUserToAction extends Hashout<EntityAction> {
  public HashoutUserToAction() {
    super(HashoutType.HASHOUT_USER_TO_ACTION, EntityAction.class);
  }
}
