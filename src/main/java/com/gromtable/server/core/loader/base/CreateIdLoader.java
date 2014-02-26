package com.gromtable.server.core.loader.base;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.loader.Loader;

public class CreateIdLoader extends Loader<Id> {
  public static final String ID_GENERATOR_ROW_KEY = "id_gen";
  public static final String ID_GENERATOR_NEXT_ID_COLUMN_KEY = "next_id";

  private final int dbId;
  private final int typeId;

  public CreateIdLoader(int dbId, int typeId) {
    this.dbId = dbId;
    this.typeId = typeId;
  }

  public Id genLoad() {
    long sequenceId = new CounterLoader(
        new Key(ID_GENERATOR_ROW_KEY),
        new Key(ID_GENERATOR_NEXT_ID_COLUMN_KEY), 1L).genLoad() - 1;
    return Id.genIdForDb(dbId, typeId, sequenceId);
  }
}
