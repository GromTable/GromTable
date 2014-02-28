import 'dart:html';
import 'dart:convert';
import 'package:intl/intl.dart';
import 'package:polymer/polymer.dart';
import 'user.dart';
import 'host.dart';
import 'viewercontext.dart';
import 'error.dart';

@CustomTag('loggedin-view')
class LoggedinView extends PolymerElement {
  static final USER_KEY = 'user';
    
  ViewerContext viewerContext = ViewerContext.instance;
  LoggedinView.created(): super.created() {
    // onPropertyChange(this, #viewerContext.currentUser, startLoadingLoggedinUser);
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
  
  logoutMessage() => Intl.message(
    "Logout",
    name: 'logout',
    args: [],
    desc: 'String on logout button',
    examples: {});
  
  userIsUnknownMessage() => Intl.message(
    "Please, decide whether you want to be voter or delegate:",
    name: "userIsUnknown",
    args: [],
    desc: 'Message in case when logged in user is did not choose his role',
    examples: {});
  
  becameVoterButtonMessage() => Intl.message(
      "Became voter",
      name: "becameVoterButton",
      args: [],
      desc: 'Button to became voter',
      examples: {});
  
  becameDelegateButtonMessage() => Intl.message(
      "Became delegate",
      name: "becameDelegateButton",
      args: [],
      desc: 'Button to became delegate',
      examples: {});
      
}