import 'package:polymer/polymer.dart';
import 'dart:html';
import 'host.dart';

@CustomTag('test-user-view')
class TestUserView extends PolymerElement {
  List<String> names = ["Deputat1", "Deputat2", "Voter1", "Voter2", "Voter3"];
  @published String customName = '';
  TestUserView.created() : super.created() {
  }
  
  void loginUser(event, detail, target) {
    String name = Uri.encodeQueryComponent(target.attributes['data-name']);
    String login_url = (Host.hash + '&code=' + name).substring(1);
    window.location.assign(login_url);
  }
}