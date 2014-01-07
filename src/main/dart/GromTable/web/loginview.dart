import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'viewercontext.dart';

@CustomTag('login-view')
class LoginView extends PolymerElement {
  LoginView.created() : super.created() {
    
  }
  
  developersOnly() => Intl.message(
      "At this point site is not ready yet and available only for developers. If you would like to help with development of this site. Please click this link to join facebook group for developers.",
      name: 'developersOnly',
      args: [],
      desc: 'Self explanatory.',
      examples: {});
  
  login() => Intl.message(
    "Login using:",
    name: 'login',
    args: [],
    desc: 'Invitation to login into site.',
    examples: {});
  
  void loginViaFacebook() {
    ViewerContext.instance.login();
  }
}