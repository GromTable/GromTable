library devsettings;

import 'package:polymer/polymer.dart';
import 'dart:html';
import 'host.dart';

class DevSettings extends Observable {
  static const IS_LOCAL_API_KEY = 'isLocalApi';
  static DevSettings _instance = null; 
  @observable bool showSettings = false;
  @observable bool isLocalApi = false;
  
  DevSettings(this.showSettings, this.isLocalApi) {
    onPropertyChange(this, #isLocalApi, updateIsLocalApi);
  }
  
  void updateIsLocalApi() {
    window.localStorage[IS_LOCAL_API_KEY] = isLocalApi.toString();
  }
  
  static DevSettings get instance {
    if (_instance == null) {
      var clientDomain = Host.clientDomain;
      var showSettings = clientDomain.startsWith("localhost");
      print(window.localStorage[IS_LOCAL_API_KEY]);
      bool isLocalApi = window.localStorage[IS_LOCAL_API_KEY] == 'true';
      _instance = new DevSettings(showSettings, isLocalApi);
    }
    return _instance;
  }
}