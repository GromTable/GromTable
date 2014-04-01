package com.gromtable.server.core.hashout;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityType;

public enum HashoutType {
  HASHOUT_LOGIN_TOKEN_TO_USER(1L),
  HASHOUT_TOKEN_TO_USER_SESSION(2L),
  HASHOUT_USER_TO_DELEGATE(3L),
  HASHOUT_DELEGATE_TO_USER(4L),
  HASHOUT_USER_TO_DOCUMENT(5L),
  HASHOUT_DOCUMENT_TO_USER(6L),
  HASHOUT_LIST_TO_DOCUMENT(7L),
  HASHOUT_LIST_TO_JSON(8L),
  HASHOUT_USER_TO_ACTION(9L);

  private final Id id;

  HashoutType(long sequenceId) {
    this.id = Id.genIdForDb(0, EntityType.ENTITY_HASHOUT.ordinal(), sequenceId);
  }

  public Id getId() {
    return id;
  }
}
