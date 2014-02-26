package com.gromtable.server.core.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.gromtable.server.core.data.Id;

public class IdDeserializer implements JsonDeserializer<Id> {
  public Id deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    return Id.fromKey(json.getAsString());
  }
}
