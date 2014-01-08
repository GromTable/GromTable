package com.gromtable.server.api.core;

import java.util.Set;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.viewer.ViewerContext;

public abstract class BaseControllerRequest {
  public static final String CALLBACK_FIELD = "callback";

  private final ApiRequest apiRequest;
  private ViewerContext viewerContext = null;
  private RequestFields cachedRequestFields = null;

  public BaseControllerRequest(ApiRequest apiRequest) {
    this.apiRequest = apiRequest;
  }

  protected Boolean getBoolean(String fieldName) {
    return getRequestFieldByName(fieldName).getBoolean(apiRequest, fieldName);
  }

  protected String getString(String fieldName) {
    return getRequestFieldByName(fieldName).getString(apiRequest, fieldName);
  }

  protected Id getId(String fieldName) {
    return getRequestFieldByName(fieldName).getId(apiRequest, fieldName);
  }

  public String getCallback() {
    return getString(CALLBACK_FIELD);
  }

  public String getReferer() {
    return apiRequest.getReferer();
  }

  private RequestFields getCachedRequestFields() {
    if (cachedRequestFields == null) {
      cachedRequestFields = getRequestFields();
      cachedRequestFields.add(new RequestField(
        CALLBACK_FIELD,
        RequestField.Type.STRING,
        RequestField.Optionality.OPTIONAL,
        "if specified, then this callback will be called"));
    }
    return cachedRequestFields;
  }

  public boolean isValid() {
    RequestFields requestFields = getCachedRequestFields();
    Set<String> keys = apiRequest.getKeys();

    // All keys defined
    for (String key : keys) {
      if (!requestFields.containsKey(key)) {
        return false;
      }
    }

    // All required keys present
    for (RequestField requestField : getRequestFields().values()) {
      if (!requestField.isValid(apiRequest)) {
        return false;
      }
    }
    return true;
  }

  protected abstract RequestFields getRequestFields();

  protected RequestField getRequestFieldByName(String name) {
    return getCachedRequestFields().get(name);
  }

  public ViewerContext getViewerContext() {
    return viewerContext;
  }

  public void setViewerContext(ViewerContext viewerContext) {
    this.viewerContext = viewerContext;
  }

  public String toString() {
    return apiRequest.toString();
  }
}
