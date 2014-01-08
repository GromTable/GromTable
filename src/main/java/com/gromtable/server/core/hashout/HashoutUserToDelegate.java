package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityUserAndVote;

public class HashoutUserToDelegate extends Hashout<EntityUserAndVote> {
  public HashoutUserToDelegate() {
    super(HashoutType.HASHOUT_USER_TO_DELEGATE, EntityUserAndVote.class);
  }
}
