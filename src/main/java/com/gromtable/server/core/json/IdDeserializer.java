package com.gromtable.server.core.json;

import java.lang.reflect.Type;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.gromtable.server.core.data.Id;

public class IdDeserializer implements JsonDeserializer<Id> {
  public Id deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
    try {
      return Id.fromRowData(Hex.decodeHex(json.getAsString().toCharArray()));
    } catch (DecoderException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
