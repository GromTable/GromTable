package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Pair;

public class EntityUser extends EntityObject<EntityUser> {
  private UserType type;
  private String fbId;
  private String name;
  private String description;
  private String birthday;
  private String city;
  private String zip;
  private String phone;
  private String facebook;
  private String vkontakte;
  private String instagram;
  private String twitter;
  private String google;

  public EntityUser() {
  }

  public EntityUser(String fbId, String name) {
    this.type = UserType.VOTER;
    this.fbId = fbId;
    this.name = name;
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_USER;
  }

  public void setType(UserType type) {
    this.type = type;
  }

  public UserType getType() {
    return type;
  }

  public String getFbId() {
    return fbId;
  }

  public String getTestId() {
    return "" + name.hashCode();
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCity() {
    return city;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public String getZip() {
    return zip;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPhone() {
    return phone;
  }

  public void setFacebook(String facebook) {
    this.facebook = facebook;
  }

  public String getFacebook() {
    return facebook;
  }

  public void setVkontakte(String vkontakte) {
    this.vkontakte = vkontakte;
  }

  public String getVkontakte() {
    return vkontakte;
  }

  public void setInstagram(String instagram) {
    this.instagram = instagram;
  }

  public String getInstagram() {
    return instagram;
  }

  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  public String getTwitter() {
    return twitter;
  }

  public void setGoogle(String google) {
    this.google = google;
  }

  public String getGoogle() {
    return google;
  }

  public static EntityUser load(final Id id) {
    return EntityObject.load(id, EntityUser.class);
  }

  public static Pair<EntityUser> load(final Pair<Id> ids) {
    return EntityObject.load(ids, EntityUser.class);
  }

  public void copyNotNullProperties(EntityUser userInfo) {
    if (userInfo.type != null) {
      this.type = userInfo.type;
    }
    if (userInfo.fbId != null) {
      this.fbId = userInfo.fbId;
    }
    if (userInfo.name != null) {
      this.name = userInfo.name;
    }
    if (userInfo.description != null) {
      this.description = userInfo.description;
    }
    if (userInfo.birthday != null) {
      this.birthday = userInfo.birthday;
    }
    if (userInfo.city != null) {
      this.city = userInfo.city;
    }
    if (userInfo.zip != null) {
      this.zip = userInfo.zip;
    }
    if (userInfo.phone != null) {
      this.phone = userInfo.phone;
    }
    if (userInfo.facebook != null) {
      this.facebook = userInfo.facebook;
    }
    if (userInfo.vkontakte != null) {
      this.vkontakte = userInfo.vkontakte;
    }
    if (userInfo.instagram != null) {
      this.instagram = userInfo.instagram;
    }
    if (userInfo.twitter != null) {
      this.twitter = userInfo.twitter;
    }
    if (userInfo.google != null) {
      this.google = userInfo.google;
    }
  }
}
