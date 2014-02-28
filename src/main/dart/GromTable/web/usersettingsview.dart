import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'dart:convert';
import 'viewercontext.dart';
import 'host.dart';
import 'state.dart';
import 'dart:html';
import 'error.dart';

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
      Host.apiDomain,
      '/api/set_user_info',
      {
        'user_type': userType,
      }
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
      .then(onSetUserInfoDone)
      .catchError((var error) {
        ErrorHandler.handleError(error);
    });
  }
  
  void onSetUserInfoDone(String response) {
    Map map = JSON.decode(response);
    ErrorHandler.handleResponse(map);
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
  
  nameMessage() => Intl.message(
      "Name: ",
      name: 'name',
      args: [],
      desc: 'Label for name.',
      examples: {});
  
  ageMessage() => Intl.message(
      "Age: ",
      name: 'age',
      args: [],
      desc: 'Label for age.',
      examples: {});
  
  cityMessage() => Intl.message(
      "City: ",
      name: 'city',
      args: [],
      desc: 'Label for city.',
      examples: {});
  
  zipMessage() => Intl.message(
      "Zip: ",
      name: 'zip',
      args: [],
      desc: 'Label for zip.',
      examples: {});
  
  descriptionMessage() => Intl.message(
      "Description: ",
      name: 'description',
      args: [],
      desc: 'Label for description.',
      examples: {});
  
  phoneMessage() => Intl.message(
      "Phone: ",
      name: 'phone',
      args: [],
      desc: 'Label for phone.',
      examples: {});
  
  facebookMessage() => Intl.message(
      "Facebook: ",
      name: 'facebook',
      args: [],
      desc: 'Label for Facebook.',
      examples: {});
  
  vkontakteMessage() => Intl.message(
      "Vkontakte: ",
      name: 'vkontkte',
      args: [],
      desc: 'Label for Vkontakte.',
      examples: {});
  
  instagramMessage() => Intl.message(
      "Instagram: ",
      name: 'instagram',
      args: [],
      desc: 'Label for Instagram.',
      examples: {});
  
  twitterMessage() => Intl.message(
      "Twitter: ",
      name: 'twitter',
      args: [],
      desc: 'Label for Twitter.',
      examples: {});
  
  namePlaceholder() => Intl.message(
      "Put your name here...",
      name: 'namePlaceholder',
      args: [],
      desc: 'Name placeholder.',
      examples: {});
  
  agePlaceholder() => Intl.message(
      "Put your age here...",
      name: 'agePlaceholder',
      args: [],
      desc: 'Age placeholder.',
      examples: {});
  
  cityPlaceholder() => Intl.message(
      "Put your city here...",
      name: 'cityPlaceholder',
      args: [],
      desc: 'City placeholder.',
      examples: {});
  
  zipPlaceholder() => Intl.message(
      "Put your zip here...",
      name: 'zipPlaceholder',
      args: [],
      desc: 'Zip placeholder.',
      examples: {});
  
  descriptionPlaceholder() => Intl.message(
      "Put your description here...",
      name: 'descriptionPlaceholder',
      args: [],
      desc: 'Description placeholder.',
      examples: {});
  
  phonePlaceholder() => Intl.message(
      "Put your phone here...",
      name: 'phonePlaceholder',
      args: [],
      desc: 'Phone placeholder.',
      examples: {});
  
  facebookPlaceholder() => Intl.message(
      "Put your facebook here...",
      name: 'facebookPlaceholder',
      args: [],
      desc: 'Facebook placeholder.',
      examples: {});
  
  vkontaktePlaceholder() => Intl.message(
      "Put your vkontakte here...",
      name: 'vkontaktePlaceholder',
      args: [],
      desc: 'Vkontakte placeholder.',
      examples: {});
  
  instagramPlaceholder() => Intl.message(
      "Put your instagram here...",
      name: 'instagramPlaceholder',
      args: [],
      desc: 'Instagram placeholder.',
      examples: {});
  
  twitterPlaceholder() => Intl.message(
      "Put your twitter here...",
      name: 'twitterPlaceholder',
      args: [],
      desc: 'Twitter placeholder.',
      examples: {});
   
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