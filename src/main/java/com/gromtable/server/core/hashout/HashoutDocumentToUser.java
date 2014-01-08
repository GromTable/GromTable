package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityUserAndVote;

public class HashoutDocumentToUser extends Hashout<EntityUserAndVote> {
  public HashoutDocumentToUser() {
    super(HashoutType.HASHOUT_DOCUMENT_TO_USER, EntityUserAndVote.class);
  }
}
