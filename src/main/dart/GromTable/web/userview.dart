import 'package:polymer/polymer.dart';
import 'user.dart';
import 'state.dart';

@CustomTag('user-view')
class UserView extends PolymerElement {
  @published User user = null;

  UserView.created() : super.created() {
  }
  
  enteredView() {
    super.enteredView();
  }
  
  void userClick(event, detail, target) {
    String userId = target.attributes['data-user'];
    State.instance = new State(State.USER, id: userId);
  }
}
