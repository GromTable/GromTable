package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityDocument;

public class HashoutListToDocument extends Hashout<EntityDocument> {
  public HashoutListToDocument() {
    super(HashoutType.HASHOUT_LIST_TO_DOCUMENT, EntityDocument.class);
  }
}
