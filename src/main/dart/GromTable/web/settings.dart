library settings;

import 'host.dart';
import 'dart:html';
import 'dart:convert';

class Settings {
  static const SETTINGS_KEY = 'settings';
  static var _settings = null;
  static void neeedSettings(var callback) {
    if (_settings == null) {
      var url = '${Host.origin}/api/settings';
      HttpRequest.getString(url).then(parseSettings).then(callback);
    } else {
      callback(_settings);
    }
  }
  
  static parseSettings(var response) {
    return _settings = JSON.decode(response)[SETTINGS_KEY];
  }
}