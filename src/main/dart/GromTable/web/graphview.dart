import 'dart:html';
import 'dart:convert';
import 'package:intl/intl.dart';
import 'package:polymer/polymer.dart';
import 'user.dart';
import 'viewercontext.dart';
import 'host.dart';

@CustomTag('graph-view')
class GraphView extends PolymerElement {
  static final USER_KEY = 'user';
  static final USER_DELEGATE_KEY = 'userDelegate';
  static final DELEGATED_USER_KEY = 'delegatedUser';
  static final DELEGATED_USER_DELEGATE_KEY = 'delegatedUserDelegate';
  
  @published bool isLoaded = false;
  @published User user = null;
  @published User userDelegate = null;
  @published List<User> delegatedUser = null;
  @published List<User> delegatedUserDelegate = null;
  @published String userDelegateId = "";
  GraphView.created(): super.created() {
  }
  
  enteredView() {
    startLoadingGraph();
    super.enteredView();
  }
  
  void logout(event, detail, target) {
    ViewerContext.instance.logout();
  }
  
  List<User> loadUserList(List<Map<String, String>> list) {
    List<User> userList = [];
    for (var item in list) {
      userList.add(new User.fromMap(item));
    }
    return userList;
  }
  
  User loadUser(map) {
    if (map != null) {
      return new User.fromMap(map);
    }
    return null;
  }
  
  void loadGraph(userId) {
    startLoadingGraph(userId);
  }
  
  void delegateUser(event, detail, target) {
    startDelegateUser(userDelegateId);
  }
  
  void startDelegateUser(String userId) {
    var url = '${Host.origin}/api/delegate';
    if (userId != null) {
      url = url + "?delegate_to=" + userId;
    }
    var request = HttpRequest.getString(url, withCredentials: true).then(onDelegateUserDone);   
  }
  
  void onDelegateUserDone(String response) {
    Map map = JSON.decode(response);
    var success = map['success'];
    if (success) {
      startLoadingGraph();
    } else {
      print(response);
    }
  }
  
  void startLoadingGraph([String userId = null]) {
    isLoaded = false;
    var url = '${Host.origin}/api/getgraph';
    if (userId != null) {
      url = url + "?user_id=" + userId;
    }
    var request = HttpRequest.getString(url, withCredentials: true).then(onDataLoaded);    
  }
  
  void onDataLoaded(String response) {
    Map map = JSON.decode(response);
    isLoaded = true;
    user = new User.fromMap(map[USER_KEY]);
    userDelegate = loadUser(map[USER_DELEGATE_KEY]);
    delegatedUser = loadUserList(map[DELEGATED_USER_KEY]);
    delegatedUserDelegate = loadUserList(map[DELEGATED_USER_DELEGATE_KEY]);
  }
  
  loading() => Intl.message(
      "Loading...",
      name: 'loading',
      args: [],
      desc: 'Indicate that user need to wait while site is loading',
      examples: {});
  
  userInformation() => Intl.message(
      "User information",
      name: 'userInformation',
      args: [],
      desc: 'Header for page where information about user displayed',
      examples: {});
  
  logoutMessage() => Intl.message(
      "Logout",
      name: 'logout',
      args: [],
      desc: 'String on logout button',
      examples: {});
  
  delegatedByUser() => Intl.message(
      "This user delegated:",
      name: 'delegatedByUser',
      args: [],
      desc: 'String before user that was delegated by',
      examples: {});
  
  delegatedThisUser() => Intl.message(
      "People that delegated this user:",
      name: 'delegatedThisUser',
      args: [],
      desc: 'String before list of users that was delegated this user',
      examples: {});
  
  delegate() => Intl.message(
      "Delegate by user id",
      name: 'delegate',
      args: [],
      desc: 'String on the button for delegating some user',
      examples: {});
}
