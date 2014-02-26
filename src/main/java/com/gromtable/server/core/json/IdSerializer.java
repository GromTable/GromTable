package com.gromtable.server.core.json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.gromtable.server.core.data.Id;

public class IdSerializer implements JsonSerializer<Id> {
  public JsonElement serialize(Id id, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(id.getString());
  }
}