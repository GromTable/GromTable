library state;

import 'package:polymer/polymer.dart';
import 'dart:html';
import 'host.dart';
import 'settings.dart';
import 'document.dart';
import 'viewercontext.dart';

class State extends Observable {
  static final String DELIMITER = '-';
  static final DOCUMENT = 'document';
  static final USER = 'user';
  static final CREATE_DOCUMENT = 'create_document';
  static final CHANGE_DOCUMENT = 'change_document';
  static final DOCUMENTS_LIST = 'documents_list';
  static final DOCUMENT_CHANGES = 'document_changes';
  static final USER_SETTINGS = 'user_settings';
  static final WELCOME = 'welcome';
  static final TRANSLATION = 'translation';
  static final UNKNOWN = 'unknown';
  
  static State _instance = new State.fromString(Host.hash);
  static bool _isListen = false;

  @observable String view = null;
  @observable String id = null;
  @observable int time = null;
  @observable DocumentInfo baseDocument = null;
  
  State(this.view, {this.id : null, this.time : null}) {
  }
  
  State.fromString(String str) {
    List<String> parts = str.split(DELIMITER);
    this.view = UNKNOWN;
    if (parts.length >= 1 && parts[0].length > 0) {
      this.view = parts[0];
    }
    if (parts.length >= 2) {
      this.id = parts[1];
    }
    if (parts.length >= 3) {
      this.time = int.parse(parts[2]);
    }
    if (view == State.UNKNOWN) {
      if (!ViewerContext.instance.isLoggedIn) {
        this.view = State.WELCOME;
        this.id = '';
      } else {
        Settings.neeedSettings(goToStatut);
      }
    }
    if (State._isListen == false) {
      State._isListen = true;
      window.onPopState.listen((_) {
        if (State.instance.toString() != Host.hash) {
          State.instance = new State.fromString(Host.hash);
        }
      });
    }
  }
  
  void goToStatut(Map<String, String> settings) {
    State.instance = new State(State.DOCUMENT, id : settings['client_status_document_id']);
  }
  
  static State get instance {
    return _instance;
  }
  
  static void set instance(State state) {
    State.instance.view = state.view;
    State.instance.id = state.id;
    State.instance.time = state.time;
    Host.hash = state.toString();
  }
  
  String toString() {
    List<String> parts = [];
    if (view != UNKNOWN) {
      parts.add(view);
    }
    if (id != null) {
      parts.add(id);
    }
    if (time != null) {
      parts.add(time.toString());
    }
    return parts.join(DELIMITER);
  }
}