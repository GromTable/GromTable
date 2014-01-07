library user;

class User {
  static final NAME_KEY = 'name';
  static final ID_KEY = 'id';
  
  String name;
  String id;
  
  User.fromMap(Map<String, String> map) {
    this.name = map[NAME_KEY];
    this.id = map[ID_KEY];
  }
  
  String getId() {
    return id;
  }
}