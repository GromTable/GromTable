library user;
import 'package:polymer/polymer.dart';

class User extends Observable {
  static User currentUser = null;
  static final ID_KEY = 'id';
  static final FB_ID_KEY = 'fbId';
  static final TYPE_KEY = 'type';
  static final NAME_KEY = 'name';
  static final DESCRIPTION_KEY = 'description';
  static final BIRTHDAY_KEY = 'birthday';
  static final CITY_KEY = 'city';
  static final ZIP_KEY = 'zip';
  static final PHONE_KEY = 'phone';
  static final FACEBOOK_KEY = 'facebook';
  static final VKONTAKTE_KEY = 'vkontakte';
  static final INSTAGRAM_KEY = 'instagram';
  static final TWITTER_KEY = 'twitter';
  static final GOOGLE_KEY = 'google';
  
  @observable String id;
  @observable String fbId;
  @observable String type;
  @observable String name;
  @observable String description;
  @observable String birthday;
  @observable String city;
  @observable String zip;
  @observable String phone;
  @observable String facebook;
  @observable String vkontakte;
  @observable String instagram;
  @observable String twitter;
  @observable String google;
  
  User.fromMap(Map<String, String> map) {
    this.id = map[ID_KEY];
    this.fbId = map[FB_ID_KEY];
    this.type = map[TYPE_KEY];
    this.name = map[NAME_KEY];
    this.description = map[DESCRIPTION_KEY];
    this.birthday = map[BIRTHDAY_KEY];
    this.city = map[CITY_KEY];
    this.zip = map[ZIP_KEY];
    this.phone = map[PHONE_KEY];
    this.facebook = map[FACEBOOK_KEY];
    this.vkontakte = map[VKONTAKTE_KEY];
    this.instagram = map[INSTAGRAM_KEY];
    this.twitter = map[TWITTER_KEY];
    this.google = map[GOOGLE_KEY];
  }
  
  Map<String, String> getInfoMap() {
    return {
      NAME_KEY : name,
      DESCRIPTION_KEY : description,
      BIRTHDAY_KEY : birthday,
      CITY_KEY : city,
      ZIP_KEY : zip,
      PHONE_KEY : phone,
      FACEBOOK_KEY : facebook,
      VKONTAKTE_KEY : vkontakte,
      INSTAGRAM_KEY : instagram,
      TWITTER_KEY : twitter,
      GOOGLE_KEY : google,
    };
  }
  
  @observable
  String getPictureUrl() {
    if (fbId != null) {
      return 'http://graph.facebook.com/${fbId}/picture?type=square';
    }
    return 'http://leadersinheels.com/wp-content/uploads/facebook-default.jpg';
  }
  
  String getId() {
    return id;
  }
}