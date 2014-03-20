package com.gromtable.server.api.setuserinfo;

import com.gromtable.server.api.RequestField;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.api.core.BaseControllerRequest;
import com.gromtable.server.api.core.RequestFields;
import com.gromtable.server.core.entity.UserType;

public class SetUserInfoRequest extends BaseControllerRequest {
  private static final String TYPE_KEY = "type";
  private static final String NAME_KEY = "name";
  private static final String DESCRIPTION_KEY = "description";
  private static final String BIRTHDAY_KEY = "birthday";
  private static final String CITY_KEY = "city";
  private static final String ZIP_KEY = "zip";
  private static final String PHONE_KEY = "phone";
  private static final String FACEBOOK_KEY = "facebook";
  private static final String VKONTAKTE_KEY = "vkontakte";
  private static final String INSTAGRAM_KEY = "instagram";
  private static final String TWITTER_KEY = "twitter";
  private static final String GOOGLE_KEY = "google";

  public SetUserInfoRequest(ApiRequest apiRequest) {
    super(apiRequest);
  }

  protected RequestFields getRequestFields() {
    RequestFields requestFields = new RequestFields();
    requestFields.add(new RequestField(
      TYPE_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user type for user."));
    requestFields.add(new RequestField(
      NAME_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user name for user."));
    requestFields.add(new RequestField(
      DESCRIPTION_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user description for user."));
    requestFields.add(new RequestField(
      BIRTHDAY_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user birthday for user."));
    requestFields.add(new RequestField(
      CITY_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user city for user."));
    requestFields.add(new RequestField(
      ZIP_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user zip for user."));
    requestFields.add(new RequestField(
      PHONE_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user phone for user."));
    requestFields.add(new RequestField(
      FACEBOOK_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user facebook for user."));
    requestFields.add(new RequestField(
      VKONTAKTE_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user vkontakte for user."));
    requestFields.add(new RequestField(
      INSTAGRAM_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user instagram for user."));
    requestFields.add(new RequestField(
      TWITTER_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user twitter for user."));
    requestFields.add(new RequestField(
      GOOGLE_KEY,
      RequestField.Type.STRING,
      RequestField.Optionality.OPTIONAL,
      "New user google for user."));
    return requestFields;
  }

  public UserType getType() {
    String type = getString(TYPE_KEY);
    if (type == null) {
      return null;
    }
    return UserType.valueOf(type.toUpperCase());
  }

  public String getName() {
    return getString(NAME_KEY);
  }

  public String getDescription() {
    return getString(DESCRIPTION_KEY);
  }

  public String getBirthday() {
    return getString(BIRTHDAY_KEY);
  }

  public String getCity() {
    return getString(CITY_KEY);
  }

  public String getZip() {
    return getString(ZIP_KEY);
  }

  public String getPhone() {
    return getString(PHONE_KEY);
  }

  public String getFacebook() {
    return getString(FACEBOOK_KEY);
  }

  public String getVkontakte() {
    return getString(VKONTAKTE_KEY);
  }

  public String getInstagram() {
    return getString(INSTAGRAM_KEY);
  }

  public String getTwitter() {
    return getString(TWITTER_KEY);
  }

  public String getGoogle() {
    return getString(GOOGLE_KEY);
  }
}