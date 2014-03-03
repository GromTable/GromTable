package com.gromtable.server.core.hashout;

import com.gromtable.server.core.entity.EntityJson;

public class HashoutListToJson extends Hashout<EntityJson> {
  public HashoutListToJson() {
    super(HashoutType.HASHOUT_LIST_TO_JSON, EntityJson.class);
  }
}
