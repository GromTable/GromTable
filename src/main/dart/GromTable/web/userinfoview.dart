import 'dart:html';
import 'dart:convert';
import 'package:intl/intl.dart';
import 'package:polymer/polymer.dart';
import 'user.dart';
import 'useraction.dart';
import 'host.dart';
import 'state.dart';
import 'viewercontext.dart';
import 'error.dart';
import 'runonce.dart';

@CustomTag('user-info-view')
class UserInfoView extends PolymerElement {
  static final USER_KEY = 'user';
  static final USER_ACTIONS_KEY = 'userActions';
  static final DELEGATE_KEY = 'delegate';
  static final USER_VOTES_KEY = 'userVotes';
  
  RunOnce runOnce = new RunOnce();

  @published String userid = null;
  @published int time = null;
  
  @observable bool isLoaded = false;
  @observable User user = null;
  @observable List<UserAction> userActions = null;
  @observable User delegate = null;
  @observable List<User> userVotes = null;
  @observable ViewerContext viewerContext = ViewerContext.instance;

  UserInfoView.created(): super.created() {
  }
  
  enteredView() {
    super.enteredView();
  }
  
  attributeChanged(String name, String oldValue, String newValue) {
    runOnce.schedule(() => startLoadingUserInfo(userid, time));
    super.attributeChanged(name, oldValue, newValue);
  }
  
  List<UserAction> loadActionList(List<Map<String, Object>> list) {
    if (list == null) {
      return null;
    }
    List<UserAction> actionList = [];
    for (var item in list) {
      actionList.add(new UserAction.fromMap(item));
    }
    return actionList;
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
    State.instance = new State(State.USER, id: userId);
  }
  
  void voteUser(event, detail, target) {
    startVoteUser(user.id);
  }
  
  void startVoteUser(String delegateId) {
    isLoaded = false;
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/vote_user',
      {
        'delegate_id': delegateId,
        'vote_decision': 'delegate',
      }
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
      .then(onVoteUserDone)
      .catchError((var error) {
        ErrorHandler.handleError(error);
    }); 
  }
  
  void onVoteUserDone(String response) {
    Map map = JSON.decode(response);
    ErrorHandler.handleResponse(map);
    var success = map['success'];
    if (success) {
      startLoadingUserInfo(userid, time);
    }
  }
  
  void startLoadingUserInfo(String userId, int time) {
    isLoaded = false;
    if (time == null) {
      time = 0;
    }
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/get_user_info',
      {
        'user_id': userId,
        'show_votes': true.toString(),
        'votes_time': time.toString(),
      }
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
      .then(onUserInfoLoaded)
      .catchError((var error) {
        ErrorHandler.handleError(error);
    });
  }

  void onUserInfoLoaded(String response) {
    Map map = JSON.decode(response);
    ErrorHandler.handleResponse(map);
    user = new User.fromMap(map[USER_KEY]);
    userActions = loadActionList(map[USER_ACTIONS_KEY]);
    delegate = loadUser(map[DELEGATE_KEY]);
    userVotes = loadUserList(map[USER_VOTES_KEY]);
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
