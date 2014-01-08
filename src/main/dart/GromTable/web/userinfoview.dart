import 'dart:html';
import 'dart:convert';
import 'package:intl/intl.dart';
import 'package:polymer/polymer.dart';
import 'user.dart';
import 'host.dart';
import 'state.dart';
import 'viewercontext.dart';

@CustomTag('user-info-view')
class UserInfoView extends PolymerElement {
  static final USER_KEY = 'user';
  static final DELEGATE_KEY = 'delegate';
  static final USER_VOTES_KEY = 'userVotes';
  static final DELEGATE_VOTES_KEY = 'delegateVotes';
  
  @published bool isLoaded = false;
  @published String userid = null;
  @observable User user = null;
  @observable User delegate = null;
  @observable List<User> userVotes = null;
  @observable List<User> delegateVotes = null;
  @observable ViewerContext viewerContext = ViewerContext.instance;

  UserInfoView.created(): super.created() {
  }
  
  enteredView() {
    print('enterView');
    //startLoadingUserInfo(userid);
    super.enteredView();
  }
  
  attributeChanged(String name, String oldValue, String newValue) {
    print('attributeChanged ' + oldValue + " " + newValue + " " + userid);
    startLoadingUserInfo(userid);
    super.attributeChanged(name, oldValue, newValue);
  }
  
  List<User> loadUserList(List<Map<String, String>> list) {
    if (list == null) {
      return null;
    }
    List<User> userList = [];
    for (var item in list) {
      userList.add(new User.fromMap(item));
    }
    return userList;
  }
  
  User loadUser(map) {
    if (map == null) {
      return null;
    }
    return new User.fromMap(map);
  }
  
  void loadGraph(userId) {
    State.instance = new State(State.USER, userId);
  }
  
  void voteUser(event, detail, target) {
    startVoteUser(user.id);
  }
  
  void startVoteUser(String delegateId) {
    isLoaded = false;
    Uri uri = new Uri.http(
      Host.serverDomain,
      '/api/vote_user',
      {
        'delegate_id': delegateId,
        'vote_decision': 'delegate',
      }
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
      .then(onVoteUserDone)
      .catchError((var error) {
        print(error.toString());
    }); 
  }
  
  void onVoteUserDone(String response) {
    Map map = JSON.decode(response);
    var success = map['success'];
    if (success) {
      startLoadingUserInfo(State.instance.id);
    } else {
      print(response);
    }
  }
  
  void startLoadingUserInfo(String userId, [votesTime = 0]) {
    isLoaded = false;
    Uri uri = new Uri.http(
      Host.serverDomain,
      '/api/get_user_info',
      {
        'user_id': userId,
        'show_votes': true.toString(),
        'votes_time': votesTime.toString(),
      }
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
      .then(onUserInfoLoaded)
      .catchError((var error) {
        print(error.toString());
    });
  }

  void onUserInfoLoaded(String response) {
    print(response);
    Map map = JSON.decode(response);
    user = new User.fromMap(map[USER_KEY]);
    delegate = loadUser(map[DELEGATE_KEY]);
    userVotes = loadUserList(map[USER_VOTES_KEY]);
    delegateVotes = loadUserList(map[DELEGATE_VOTES_KEY]);
    isLoaded = true;
  }
  
  loading() => Intl.message(
      "Loading...",
      name: 'loading',
      args: [],
      desc: 'Indicate that user need to wait while site is loading',
      examples: {});
  
  userInformation() => Intl.message(
      "User information",
      name: 'userInformation',
      args: [],
      desc: 'Header for page where information about user displayed',
      examples: {});
  
  delegatedByUser() => Intl.message(
      "This user delegated:",
      name: 'delegatedByUser',
      args: [],
      desc: 'String before user that was delegated by',
      examples: {});
  
  delegatedThisUser() => Intl.message(
      "People that delegated this user:",
      name: 'delegatedThisUser',
      args: [],
      desc: 'String before list of users that was delegated this user',
      examples: {});
  
  delegateMessage() => Intl.message(
      "Delegate by user id",
      name: 'delegate',
      args: [],
      desc: 'String on the button for delegating some user',
      examples: {});
  
  askToBeDelegateMessage() => Intl.message(
      "Ask him to became a delegate if you trust him",
      name: 'askToBeDelegate',
      args: [],
      desc: 'Message saying that this is not a delegate',
      examples: {});
}
