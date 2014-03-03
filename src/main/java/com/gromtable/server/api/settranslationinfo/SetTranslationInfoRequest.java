package com.gromtable.server.api.settranslationinfo;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.api.gettranslationinfo.IntlMessagesType;

public class SetTranslationInfoRequest extends BaseControllerRequest {
  private static final String INTL_MESSAGES_SOURCE = IntlMessagesType.INTL_MESSAGES_SOURCE.getName();
  private static final String INTL_MESSAGES_UK = IntlMessagesType.INTL_MESSAGES_UK.getName();
  private static final String INTL_MESSAGES_RU = IntlMessagesType.INTL_MESSAGES_RU.getName();
  private static final String INTL_MESSAGES_EN = IntlMessagesType.INTL_MESSAGES_EN.getName();

  public SetTranslationInfoRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      INTL_MESSAGES_SOURCE,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "Source for translation."));
    requestFields.add(new RequestField(
      INTL_MESSAGES_UK,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "Ukrainian version of translation."));
    requestFields.add(new RequestField(
      INTL_MESSAGES_RU,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "Russian version of translation."));
    requestFields.add(new RequestField(
      INTL_MESSAGES_EN,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "English version of translation."));
    return requestFields;
  }

  public String getIntlMessagesSource() {
    return getString(INTL_MESSAGES_SOURCE);
  }

  public String getIntlMessagesUk() {
    return getString(INTL_MESSAGES_UK);
  }

  public String getIntlMessagesRu() {
    return getString(INTL_MESSAGES_RU);
  }

  public String getIntlMessagesEn() {
    return getString(INTL_MESSAGES_EN);
  }
}