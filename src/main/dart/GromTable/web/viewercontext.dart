library viewercontrext;

import 'package:polymer/polymer.dart';
import 'dart:html';
import 'dart:core';
import 'host.dart';
import 'settings.dart';
import 'user.dart';
import 'package:cookie/cookie.dart' as cookie;

class ViewerContext extends Observable {
  static final String SESSION_KEY = 's';
  static final String FACEBOOK_CLIENT_ID = 'client_facebook_client_id';
  static ViewerContext instance = new ViewerContext();
  
  String _sessionKey;
  @observable bool isLoggedIn;
  @observable User currentUser;
  
  ViewerContext() {
    readLoginInformation();
  }
  
  void readLoginInformation() {
    _sessionKey = cookie.get(SESSION_KEY);
    isLoggedIn = _sessionKey != null;
  }
  
  void logout() {
    cookie.remove(SESSION_KEY, path: '/');
    currentUser = null;
    readLoginInformation();
  }
  
  void loginViaFacebook() {
    Settings.neeedSettings(loginWithSettings);
  }
  
  void loginViaTestUsers() {
    String type = Uri.encodeQueryComponent('test');
    String redirect_url = Uri.encodeQueryComponent(Host.href);
    String hash = '${Host.origin}/api/login?type=${type}&state=${redirect_url}';
    String href = Host.href;
    String test_user_url = null;
    if (href.contains('index')) {
      test_user_url = href.replaceAll('index', 'testuser');
    } else {
      test_user_url = href.replaceAll('/#', '/testuser.html#');
    }
    int hashIndex = test_user_url.indexOf('#');
    if (hashIndex > 0) {
      test_user_url = test_user_url.substring(0, hashIndex);
    }
    test_user_url += '#${hash}';
    window.location.assign(test_user_url);    
  }
  
  void loginWithSettings(Map<String, String> settings) {
    String fb_base_url = 'https://www.facebook.com/dialog/oauth';
    String client_id = settings[FACEBOOK_CLIENT_ID];
    String response_type = 'code';
    String redirect_url = '${Host.origin}/api/login?type=facebook';
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