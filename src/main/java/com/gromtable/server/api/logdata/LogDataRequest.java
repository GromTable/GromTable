package com.gromtable.server.api.logdata;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.environment.BaseEnvironment;

public class LogDataRequest extends BaseControllerRequest {
  public static final String CATEGORY_FIELD = "category";
  public static final String DATA_FIELD = "data";
  private static RequestFields requestFields = null;
  private class HashMapTypeToken extends TypeToken<HashMap<String, String>> {
  }

  private final Type type = new HashMapTypeToken().getType();

  public LogDataRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    if (requestFields == null) {
      requestFields = getBaseRequestFields();
      requestFields.add(new RequestField(
        CATEGORY_FIELD,
        RequestField.Type.STRING,
        RequestField.Optionality.REQUIRED,
        "This is category to which data need to be logged."));
      requestFields.add(new RequestField(
        DATA_FIELD,
        RequestField.Type.STRING,
        RequestField.Optionality.REQUIRED,
        "This is data."));
    }
    return requestFields;
  }

  public String getCategory() {
    return getString(CATEGORY_FIELD);
  }

  public Map<String, String> getData() {
    Gson gson = BaseEnvironment.getEnvironment().getGson();
    final Map<String, String> data = gson.fromJson(getString(DATA_FIELD), type);
    return data;
  }
}
