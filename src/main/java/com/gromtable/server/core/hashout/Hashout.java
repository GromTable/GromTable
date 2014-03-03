package com.gromtable.server.core.hashout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.gromtable.server.core.data.Columns;
import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.entity.EntityObject;
import com.gromtable.server.core.entity.EntityUserAndVote;
import com.gromtable.server.core.loader.base.HashoutAddLoader;
import com.gromtable.server.core.loader.base.HashoutGetMultiLoader;
import com.gromtable.server.core.loader.base.HashoutRemoveLoader;

// TODO: add interfaces with using Id instead of Key.
public abstract class Hashout<T extends EntityObject<T>> {
  private Id hashoutId;
  private Class<T> clazz;

  public Hashout(HashoutType hashoutType, Class<T> clazz) {
    this.hashoutId = hashoutType.getId();
    this.clazz = clazz;
  }

  public Id getHashoutId() {
    return hashoutId;
  }

  private <Obj> Obj getOneValue(List<Obj> objects) {
    if (objects.size() == 0) {
      return null;
    }

    if (objects.size() == 1) {
      return objects.get(0);
    }

    throw new NullPointerException();
  }

  public List<HashoutRecord> loadRecords(final Key fromKey) {
    Columns columns = new HashoutGetMultiLoader(hashoutId.getKey(), fromKey).genLoad();
    List<HashoutRecord> entries = new ArrayList<HashoutRecord>();
    if (columns != null) {
      for (Map.Entry<Key, Data> entry : columns.entrySet()) {
        entries.add(new HashoutRecord(entry.getKey(), 0));
      }
    }
    return entries;
  }

  public Map<Key, List<HashoutRecord>> loadMultiRecords(final List<Key> fromKeys) {
    Map<Key, List<HashoutRecord>> multiRecords = new TreeMap<Key, List<HashoutRecord>>();

    for (Key fromKey : fromKeys) {
      multiRecords.put(fromKey, loadRecords(fromKey));
    }

    return multiRecords;
  }

  public Map<Key, HashoutRecord> loadMultiRecord(final List<Key> fromKeys) {
    Map<Key, List<HashoutRecord>> multiHashoutEntries = loadMultiRecords(fromKeys);
    Map<Key, HashoutRecord> multiHashoutEntry = new TreeMap<Key, HashoutRecord>();

    for (Map.Entry<Key, List<HashoutRecord>> entry : multiHashoutEntries.entrySet()) {
      multiHashoutEntry.put(entry.getKey(), getOneValue(entry.getValue()));
    }

    return multiHashoutEntry;
  }

  public List<T> loadEntities(final Key fromKey) {
    List<HashoutRecord> hashoutEntries = loadRecords(fromKey);
    List<T> entities = new ArrayList<T>();

    for (HashoutRecord hashoutEntry : hashoutEntries) {
      if (clazz.isInstance(EntityUserAndVote.EMPTY)) {
        entities.add(EntityUserAndVote.load(hashoutEntry.getKey(), clazz));
      } else {
        Id id = Id.fromKey(hashoutEntry.getKey());
        entities.add(EntityObject.load(id, clazz));
      }
    }

    return entities;
  }

  public Map<Key, List<T>> loadMultiEntities(final List<Key> fromKeys) {
    Map<Key, List<T>> multiEntities = new HashMap<Key, List<T>>();

    for (Key fromKey : fromKeys) {
      multiEntities.put(fromKey, loadEntities(fromKey));
    }

    return multiEntities;
  }

  public T loadEntity(final Key fromKey) {
    List<T> results = loadEntities(fromKey);

    if (results.size() == 0) {
      return null;
    }

    if (results.size() > 1) {
      throw new NullPointerException();
    }

    return results.get(0);
  }

  public void removeKey(final Key fromKey, final Key toKey) {
    new HashoutRemoveLoader(hashoutId.getKey(), fromKey, toKey).genLoad();
  }

  public void addKey(final Key fromKey, final Key toKey) {
    new HashoutAddLoader(
      hashoutId, fromKey, toKey, new Data(new byte[0])).genLoad();
  }

  public void multiAddKey(final Map<Key, Key> fromToMap) {
    for (Map.Entry<Key, Key> fromToEntry : fromToMap.entrySet()) {
      addKey(fromToEntry.getKey(), fromToEntry.getValue());
    }
  }

  public void addEntity(final Key fromKey, final T entity, final int dbId) {
    entity.saveToDb(dbId);
    addKey(fromKey, entity.getId().getKey());
  }

  public void addEntity(final Key fromKey, final T entity) {
    entity.save();
    addKey(fromKey, entity.getId().getKey());
  }

  public void multiAddEntry(final Map<Key, T> entities) {
    for (Map.Entry<Key, T> entity : entities.entrySet()) {
      addEntity(entity.getKey(), entity.getValue());
    }
  }

  public String toString() {
    return "Hashout";
  }
}
