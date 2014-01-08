package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityUserAndVote;

public class HashoutDelegateToUser extends Hashout<EntityUserAndVote> {
  public HashoutDelegateToUser() {
    super(HashoutType.HASHOUT_DELEGATE_TO_USER, EntityUserAndVote.class);
  }
}
