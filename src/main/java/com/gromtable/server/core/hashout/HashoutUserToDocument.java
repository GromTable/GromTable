package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityVote;

public class HashoutUserToDocument extends Hashout<EntityVote> {
  public HashoutUserToDocument() {
    super(HashoutType.HASHOUT_USER_TO_DOCUMENT, EntityVote.class);
  }
}
