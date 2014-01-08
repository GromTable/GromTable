import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'state.dart';
import 'viewercontext.dart';

@CustomTag('commands-view')
class CommandsView extends PolymerElement {
  ViewerContext viewerContext = ViewerContext.instance;
  CommandsView.created() : super.created() {
  } 
  
  void createDocument(event, detail, target) {
    State.instance = new State(State.CREATE_DOCUMENT, '');
  }
  
  void documentsList(event, detail, target) {
    State.instance = new State(State.DOCUMENTS_LIST, '');
  }
  
  void userSettings(event, detail, target) {
    State.instance = new State(State.USER_SETTINGS, '');
  }
  
  commandsMessage() => Intl.message(
      "Commands",
      name: 'commands',
      args: [],
      desc: 'Label on create document button.',
      examples: {});
  
  createDocumentMessage() => Intl.message(
      "Create document",
      name: 'createDocument',
      args: [],
      desc: 'Label on create document button.',
      examples: {});
  
  documentsListMessage() => Intl.message(
      "Documents list",
      name: 'documentsList',
      args: [],
      desc: 'Label on show documents list button.',
      examples: {});
  
  userSettingsMessage() => Intl.message(
      "User Settings",
      name: 'userSettings',
      args: [],
      desc: 'Label on user settings button.',
      examples: {});
}