library settings;

import 'host.dart';
import 'dart:html';
import 'dart:convert';
import 'error.dart';

class Settings {
  static const SETTINGS_KEY = 'settings';
  static var _settings = null;
  static void neeedSettings(var callback) {
    if (_settings == null) {
      Uri uri = new Uri.http(
        Host.apiDomain,
        '/api/settings'
      );
      HttpRequest.getString(uri.toString())
      .then(parseSettings)
      .then(callback)
      .catchError((var error) {
        ErrorHandler.handleError(error);
       });
    } else {
      callback(_settings);
    }
  }
  
  static parseSettings(var response) {
    Map map = JSON.decode(response);
    ErrorHandler.handleResponse(map);
    return _settings = map[SETTINGS_KEY];
  }
}