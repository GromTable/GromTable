package com.gromtable.server.core.entity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.gromtable.server.core.data.Columns;
import com.gromtable.server.core.data.Data;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.RowKey;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.Environment;
import com.gromtable.server.core.loader.base.CreateIdLoader;
import com.gromtable.server.core.loader.base.EntityGetLoader;
import com.gromtable.server.core.loader.base.EntityUpdateLoader;

public abstract class EntityObject<T extends EntityObject<T>> {
  public static final String ENCODING = "ISO-8859-1";
  private Id id = null;

  public abstract EntityType getEntityType();

  public void setId(Id id) {
    this.id = id;
  }

  public Id getId() {
    return id;
  }

  public static String getKey(final Id id) {
    return "e:" + id;
  }

  private static String bytesToString(byte[] data) {
    try {
      return new String(data, ENCODING);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("No encoding");
    }
  }

  private static byte[] stringToBytes(String str) {
    try {
      return str.getBytes(ENCODING);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("No encoding");
    }
  }

  public static <T extends EntityObject<T>> T load(final Id id, final Class<T> clazz) {
    Columns columns = new EntityGetLoader(id).genLoad();
    JsonObject jsonObject = new JsonObject();
    Gson gson = BaseEnvironment.getEnvironment().getGson();
    for (Entry<Key, Data> entity : columns.entrySet()) {
      jsonObject.add(entity.getKey().stringValue(), new JsonPrimitive(bytesToString(entity.getValue().getRowData())));
    }
    return gson.fromJson(jsonObject, clazz);
  }

  public static <T extends EntityObject<T>> List<T> load(final List<Id> ids, final Class<T> clazz) {
    List<T> entities = new ArrayList<T>();

    for (Id idElement : ids) {
      entities.add(load(idElement, clazz));
    }

    return entities;
  }

  @SuppressWarnings("unchecked")
  public T saveToDb(final int dbId) {
    if (id == null) {
      int typeId = getEntityType().ordinal();
      id = new CreateIdLoader(dbId, typeId).genLoad();
    } else {
      if (dbId != id.getDbId()) {
        throw new IllegalArgumentException();
      }
    }
    Gson gson = BaseEnvironment.getEnvironment().getGson();
    JsonElement json = gson.toJsonTree(EntityObject.this);
    JsonObject jsonObject = json.getAsJsonObject();
    Id entityId = EntityObject.this.id;
    Columns columns = new Columns();
    for (Entry<String, JsonElement> column : jsonObject.entrySet()) {
      columns.put(new RowKey(column.getKey()), new Data(stringToBytes(column.getValue().getAsString())));
    }
    new EntityUpdateLoader(entityId, columns).genLoad();
    return (T) EntityObject.this;
  }

  protected int getDbId(Environment environment) {
    int dbId;
    if (id == null) {
      dbId = environment.getRandom().nextInt() % Id.MAX_DB_ID;
    } else {
      dbId = id.getDbId();
    }
    return dbId;
  }

  public T save() {
    return saveToDb(getDbId(BaseEnvironment.getEnvironment()));
  }

  public String toString() {
    Gson gson = new Gson();
    String json = gson.toJson(EntityObject.this);

    return json;
  }

  public int hashCode() {
    return toString().hashCode();
  }

  public boolean equals(Object obj) {
    return toString().equals(obj.toString());
  }
}
