import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'viewercontext.dart';
import 'state.dart';
import 'settings.dart';
import 'devsettings.dart';

@CustomTag('welcome-view')
class WelcomeView extends PolymerElement {
  DevSettings devSettings = DevSettings.instance;
  WelcomeView.created() : super.created() {
  } 
 
  welcomeHeader() => Intl.message(
      "Public table",
      name: 'welcomeHeader',
      args: [],
      desc: 'Header for welcome view.',
      examples: {});
  
  welcomeDescription() => Intl.message(
      "Nationwise, out of party project, created with idea that all people can talk to each other.\n\n"
      "Main idea for projects:\n"
      "Dovira - bla bla"
      "Dostupnist - bla bla"
      "Nationwise - bla bal",
      name: 'welcomeDescription',
      args: [],
      desc: 'Description of the project.',
      examples: {});
  
  welcomeStatus() => Intl.message(
      "Status",
      name: 'welcomeStatus',
      args: [],
      desc: 'Welcome status.',
      examples: {});
  
  welcomeLoginViaFacebook() => Intl.message(
      "Login via Facebook",
      name: 'welcomeLoginViaFacebook',
      args: [],
      desc: 'Login to site via Facebook',
      examples: {});
  
  welcomeViaTestUser() => Intl.message(
      "Login via Test User",
      name: 'welcomeLoginViaTestUser',
      args: [],
      desc: 'Login via Test User',
      examples: {});
  
  void goToStatus(event, detail, target) {
    Settings.neeedSettings(goToStatusWithSettings);
    ViewerContext.instance.logout();
  }
  
  void goToStatusWithSettings(Map<String, String> settings) {
    State.instance = new State(State.DOCUMENT, settings['client_status_document_id']);
  }
  
  void loginViaFacebook() {
    State.instance = new State(State.DOCUMENTS_LIST, '');
    ViewerContext.instance.loginViaFacebook();
  }
  
  void loginTestUsers() {
    State.instance = new State(State.DOCUMENTS_LIST, '');
    ViewerContext.instance.loginViaTestUsers();
  }
  
}