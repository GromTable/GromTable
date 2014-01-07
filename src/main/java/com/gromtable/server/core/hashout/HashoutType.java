package com.gromtable.server.core.hashout;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityType;

public enum HashoutType {
  HASHOUT_LOGIN_TOKEN_TO_USER(1L),
  HASHOUT_TOKEN_TO_USER_SESSION(2L),
  HASHOUT_USER_TO_DELEGATED_USER(3L),
  HASHOUT_DELEGATED_USER_TO_USER(4L);

  private final Id id;

  HashoutType(long sequenceId) {
    this.id = Id.genIdForDb(0, EntityType.ENTITY_HASHOUT.ordinal(), sequenceId);
  }

  public Id getId() {
    return id;
  }
}
