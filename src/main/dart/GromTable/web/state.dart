library state;

import 'package:polymer/polymer.dart';
import 'dart:html';
import 'host.dart';
import 'settings.dart';

class State extends Observable {
  static final DOCUMENT = 'document';
  static final USER = 'user';
  static final CREATE_DOCUMENT = 'create_document';
  static final DOCUMENTS_LIST = 'documents_list';
  static final USER_SETTINGS = 'user_settings';
  static final WELCOME = 'welcome';
  static final TRANSLATION = 'translation';
  static final UNKNOWN = 'unknown';
  
  static State _instance = new State.fromString(Host.hash);
  static bool _isListen = false;

  @observable String view;
  @observable String id;
  
  State(this.view, this.id) {
  }
  
  State.fromString(String str) {
    str = str.replaceAll('#', '');
    List<String> validViews = [DOCUMENT, USER, CREATE_DOCUMENT, DOCUMENTS_LIST, USER_SETTINGS, WELCOME, TRANSLATION];
    this.view = UNKNOWN;
    for (var view in validViews) {
      if (str.startsWith(view)) {
        this.view = view;
        this.id = str.substring(view.length);
      }
    }
    if (view == State.UNKNOWN) {
      Settings.neeedSettings(goToStatut);
    }
    if (State._isListen == false) {
      State._isListen = true;
      window.onPopState.listen((_) {
        State.instance = new State.fromString(Host.hash);
      });
    }
  }
  
  void goToStatut(Map<String, String> settings) {
    State.instance = new State(State.DOCUMENT, settings['client_status_document_id']);
  }
  
  static State get instance {
    return _instance;
  }
  
  static void set instance(State state) {
    State.instance.view = state.view;
    State.instance.id = state.id;
    Host.hash = state.toString();
  }
  
  String toString() {
    if (view == UNKNOWN) {
      return '';
    }
    return view + id;
  }
}