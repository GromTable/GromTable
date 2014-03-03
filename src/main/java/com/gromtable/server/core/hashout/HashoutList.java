package com.gromtable.server.core.hashout;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityType;

public enum HashoutList {
  ALL_DOCUMENTS(1L),
  INTL_MESSAGES_ALL(2L),
  INTL_MESSAGES_CURRENT(3L);

  private final Id id;

  HashoutList(long sequenceId) {
    this.id = Id.genIdForDb(0, EntityType.ENTITY_LIST.ordinal(), sequenceId);
  }

  public Id getId() {
    return id;
  }
}
