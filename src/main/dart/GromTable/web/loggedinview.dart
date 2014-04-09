import 'dart:html';
import 'dart:convert';
import 'package:intl/intl.dart';
import 'package:polymer/polymer.dart';
import 'user.dart';
import 'host.dart';
import 'viewercontext.dart';
import 'devsettings.dart';
import 'error.dart';
import 'state.dart';
import 'commonmessages.dart';

@CustomTag('loggedin-view')
class LoggedinView extends PolymerElement {
  static final USER_KEY = 'user';
    
  ViewerContext viewerContext = ViewerContext.instance;
  DevSettings devSettings = DevSettings.instance;
  LoggedinView.created(): super.created() {
  }
  
  enteredView() {
    startLoadingLoggedinUser();
    super.enteredView();
  }
  
  void startLoadingLoggedinUser() {
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/get_loggedin_user'
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials: true)
        .then(onLoggedinUserLoaded)
        .catchError((var error) {
          ErrorHandler.handleError(error);
         }); 
  }
  
  void onLoggedinUserLoaded(String response) {
    Map map = JSON.decode(response);
    ErrorHandler.handleResponse(map);
    viewerContext.currentUser = new User.fromMap(map[USER_KEY]);
  }
  
  void logout(event, detail, target) {
    ViewerContext.instance.logout();
  }

  
  void loginViaFacebook() {
    ViewerContext.instance.loginViaFacebook();
  }
  
  void loginTestUsers() {
    ViewerContext.instance.loginViaTestUsers();
  }
   
  void userSettings(event, detail, target) {
    State.instance = new State(State.USER_SETTINGS);
  }
  
  login() => Intl.message(
    "Login using:",
    name: 'login',
    args: [],
    desc: 'Invitation to login into site.',
    examples: {});
  
  userSettingsMessage() => CommonMessages.userSettingsMessage();
  
  logoutMessage() => Intl.message(
    "Logout",
    name: 'logout',
    args: [],
    desc: 'String on logout button',
    examples: {});
}