package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.loader.base.CounterLoader;

public class EntityCounters extends EntityObject<EntityCounters> {
  public static final Id COUNTERS_ID = Id.genIdForDb(0, EntityType.ENTITY_COUNTERS.ordinal(), 0L);
  private static final Key NEXT_ID_COLUMN_KEY = new Key("nextId");
  private static final Key NEXT_DOCUMENT_ID_COLUMN_KEY = new Key("nextDocumentId");

  public EntityCounters() {
  }

  public static <T extends EntityObject<T>> T load(final Id id, final Class<T> clazz) {
    throw new RuntimeException("Not implemented.");
  }

  public EntityCounters saveToDb(final int dbId) {
    throw new RuntimeException("Not implemented.");
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_COUNTERS;
  }

  public static long nextDocumentId() {
    return new CounterLoader(
        COUNTERS_ID.getKey(),
        NEXT_DOCUMENT_ID_COLUMN_KEY, 1L).genLoad();
  }

  public static long nextId() {
    return new CounterLoader(
        COUNTERS_ID.getKey(),
        NEXT_ID_COLUMN_KEY, 1L).genLoad();
  }
}
