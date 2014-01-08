library user;
import 'package:polymer/polymer.dart';

class User extends Observable {
  static User currentUser = null;
  static final NAME_KEY = 'name';
  static final ID_KEY = 'id';
  static final FB_ID_KEY = 'fbId';
  static final TYPE_KEY = 'type';
  
  @observable String name;
  @observable String id;
  @observable String fbId;
  @observable String type;
  
  User.fromMap(Map<String, String> map) {
    this.name = map[NAME_KEY];
    this.id = map[ID_KEY];
    this.fbId = map[FB_ID_KEY];
    this.type = map[TYPE_KEY];
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