library viewercontrext;

import 'package:polymer/polymer.dart';
import 'dart:html';
import 'dart:core';
import 'mainview.dart';
import 'host.dart';
import 'settings.dart';
import 'package:cookie/cookie.dart' as cookie;

class ViewerContext {
  static final String SESSION_KEY = 's';
  static final String FACEBOOK_CLIENT_ID = 'client_facebook_client_id';
  @published static ViewerContext instance = new ViewerContext();
  
  String _sessionKey;
  @published bool isLoggedIn;
  
  ViewerContext() {
    readLoginInformation();
  }
  
  void readLoginInformation() {
    _sessionKey = cookie.get(SESSION_KEY);
    isLoggedIn = _sessionKey != null;
    if (MainView.mainView != null) {
      MainView.mainView.isLoggedIn = isLoggedIn;
    }
  }
  
  void logout() {
    cookie.remove(SESSION_KEY, path: '/');
    readLoginInformation();
  }
  
  void login() {
    Settings.neeedSettings(loginWithSettings);
  }
  
  void loginWithSettings(Map<String, String> settings) {
    String fb_base_url = 'https://www.facebook.com/dialog/oauth';
    String client_id = settings[FACEBOOK_CLIENT_ID];
    String response_type = 'code';
    String redirect_url = '${Host.origin}/api/login';
    Uri uri = new Uri.https(
      'www.facebook.com',
      'dialog/oauth',
      {
        'client_id': client_id,
        'response_type': response_type,
        'redirect_uri': redirect_url,
        'state': redirect_url + 'DELIMITER' + Host.href,
      }
    );
    String fb_auth_url = uri.toString();
    window.location.assign(fb_auth_url);
  }
}