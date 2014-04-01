import 'package:intl/intl.dart';
import 'package:polymer/polymer.dart';
import 'useraction.dart';

@CustomTag('user-actions-view')
class UserActionsView extends PolymerElement {
  @published List<UserAction> useractions;

  UserActionsView.created() : super.created() {
  }
  
  List<UserAction> sortByTime(List<UserAction> list) {
    list.sort((UserAction x, UserAction y) => y.time - x.time);
    return list;
  }
    
  userActionsMessage() => Intl.message(
      "User actions:",
      name: 'userActions',
      args: [],
      desc: 'User actions header.',
      examples: {});
}