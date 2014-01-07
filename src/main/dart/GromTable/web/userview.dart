import 'package:polymer/polymer.dart';
import 'user.dart';

@CustomTag('user-view')
class UserView extends PolymerElement {
  @published User user = null;
  @published String userName = null;
  @published String userId = null;
  @published var onuser = null;
  
  UserView.created() : super.created() {
  }
  
  enteredView() {
    userName = user.name;
    userId = user.id;
    super.enteredView();
  }
  
  void userClick(event, detail, target) {
    onuser(target.attributes['data-user']);
  }
}
