library viewercontrext;

import 'package:polymer/polymer.dart';
import 'dart:convert';
import 'dart:html';
import 'dart:core';
import 'host.dart';
import 'settings.dart';
import 'user.dart';
import 'error.dart';
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
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/logout'
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
        .then(onLogoutDone)
        .catchError((var error) {
          ErrorHandler.handleError(error);
          onLogoutDone("{}");
         });
  }
  
  void onLogoutDone(String response) {
    Map map = JSON.decode(response);
    ErrorHandler.handleResponse(map);
    cookie.remove(SESSION_KEY, path: '/');
    currentUser = null;
    readLoginInformation();    
  }
  
  void loginViaFacebook() {
    Settings.neeedSettings(loginWithSettings);
  }
  
  void loginViaTestUsers() {
    String type = Uri.encodeQueryComponent('test');
    String redirect_url = Uri.encodeQueryComponent(Host.clientHref);
    
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/login',
      {
        'type': type,
        'state': redirect_url,
      }
    );
    String hash = uri.toString();
    String href = Host.clientHref;
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
    loginVia(test_user_url);
  }
  
  void loginWithSettings(Map<String, String> settings) {
    String fb_base_url = 'https://www.facebook.com/dialog/oauth';
    String client_id = settings[FACEBOOK_CLIENT_ID];
    String response_type = 'code';
    Uri redirect_uri = new Uri.http(
      Host.apiDomain,
      '/api/login',
      {
        'type': 'facebook',
      }
    );
    String redirect_url = redirect_uri.toString();
    Uri uri = new Uri.https(
      'www.facebook.com',
      'dialog/oauth',
      {
        'client_id': client_id,
        'response_type': response_type,
        'redirect_uri': redirect_url,
        'state': redirect_url + 'DELIMITER' + Host.clientHref,
      }
    );
    String fb_auth_url = uri.toString();
    loginVia(fb_auth_url);
  }
  
  void loginVia(String authUrl) {
    cookie.set(SESSION_KEY, 'session_key', path: '/');
    window.location.assign(authUrl);
  }
}