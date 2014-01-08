import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'viewercontext.dart';
import 'host.dart';
import 'state.dart';
import 'dart:html';

@CustomTag('user-settings-view')
class UserSettingsView extends PolymerElement {
  @observable String description;
  @observable String phone;
  @observable String facebook;
  @observable String vkontakte;
  @observable String instagram;
  @observable String twittter;
  
  ViewerContext viewerContext = ViewerContext.instance;
  UserSettingsView.created() : super.created() {
  } 
  
  void updateInfo(event, detail, target) {
    window.alert('not implemented yet');
  }
  
  void startSetUserInfo(String userType) {
    Uri uri = new Uri.http(
      Host.serverDomain,
      '/api/set_user_info',
      {
        'user_type': userType,
      }
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
      .then(onSetUserInfoDone)
      .catchError((var error) {
        print(error.toString());
    });
  }
  
  void onSetUserInfoDone(String response) {
    State.instance = new State(State.USER, viewerContext.currentUser.id);
    // Clowntown - this shoud return new user info
    window.location.reload();
  }

  void becameVoter(event, detail, target) {
    startSetUserInfo('VOTER');
  }
  
  void becameDelegate(event, detail, target) {
    startSetUserInfo('DELEGATE');
  }
  
  updateInfoMessage() => Intl.message(
      "Update user info",
      name: 'updateInfo',
      args: [],
      desc: 'Label on update user info button.',
      examples: {});
  
  voterDelegateExplanation() => Intl.message(
      "Voter can vote for the documents and delegate his vote to his delegate. Delegate need to vote for the documents daily, delegate can not delegate his vote. Once delegate get 20 votes from voters he can create docuemnts",
      name: 'becameDelegateExplanation',
      args: [],
      desc: 'Label on became delegate button.',
      examples: {}); 
  
  becameDelegateMessage() => Intl.message(
      "Became delegate",
      name: 'becameDelegate',
      args: [],
      desc: 'Label on became delegate button.',
      examples: {});   
  
  becameVoterMessage() => Intl.message(
      "Became voter",
      name: 'becameVoter',
      args: [],
      desc: 'Label on became voter button.',
      examples: {});
}