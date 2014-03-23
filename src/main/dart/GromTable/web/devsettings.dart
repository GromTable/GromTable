library devsettings;

import 'package:polymer/polymer.dart';
import 'dart:html';
import 'host.dart';

class DevSettings extends Observable {
  static const IS_LOCAL_API_KEY = 'isLocalApi';
  static const IS_DEMO_LOGIN_KEY = 'isDemoLogin';
  static const IS_RAW_TRANSLATE = 'isRawTranslate';
  static DevSettings _instance = null; 
  @observable bool showSettings = false;
  @observable bool isLocalApi = false;
  @observable bool isDemoLogin = false;
  @observable bool isRawTranslate = false;
  
  DevSettings(this.showSettings, this.isLocalApi, this.isDemoLogin, this.isRawTranslate) {
    onPropertyChange(this, #isLocalApi, updateIsLocalApi);
    onPropertyChange(this, #isDemoLogin, updateIsDemoLogin);
    onPropertyChange(this, #isRawTranslate, updateIsRawTranslate);
  }
  
  void updateIsLocalApi() {
    window.localStorage[IS_LOCAL_API_KEY] = isLocalApi.toString();
  }
  
  void updateIsDemoLogin() {
    window.localStorage[IS_DEMO_LOGIN_KEY] = isDemoLogin.toString();    
  }
  
  void updateIsRawTranslate() {
    window.localStorage[IS_RAW_TRANSLATE] = isRawTranslate.toString();      
  }
  
  static DevSettings get instance {
    if (_instance == null) {
      var clientDomain = Host.clientDomain;
      var showSettings = clientDomain.startsWith("localhost");
      bool isLocalApi = window.localStorage[IS_LOCAL_API_KEY] == 'true';
      bool isDemoLogin = window.localStorage[IS_DEMO_LOGIN_KEY] == 'true';
      bool isRawTranslate = window.localStorage[IS_RAW_TRANSLATE] == 'true';
      _instance = new DevSettings(showSettings, isLocalApi, isDemoLogin, isRawTranslate);
    }
    return _instance;
  }
}