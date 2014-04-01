library useraction;

import 'package:polymer/polymer.dart';
import 'user.dart';
import 'document.dart';

class UserAction extends Observable {
  static final ACTION_TYPE_KEY = 'actionType';
  static final TIME_KEY = 'time';
  static final ACTOR_KEY = 'actor';
  static final USER_TYPE_KEY = 'userType';
  static final DOCUMENT_KEY = 'document';
  static final DELEGATE_KEY = 'delegate';
  static final VOTE_KEY = 'vote';
  static final VOTE_DECISION_KEY = 'voteDecision';
  
  @observable String actionType;
  @observable int time;
  @observable User actor;
  @observable String userType;
  @observable DocumentInfo document;
  @observable User delegate;
  @observable String vote;
  @observable String voteDecision;

  
  UserAction.fromMap(Map<String, Object> map) {
    this.actionType = map[ACTION_TYPE_KEY];
    this.time = map[TIME_KEY];
    this.actor = new User.fromMap(map[ACTOR_KEY]);
    this.userType = map[USER_TYPE_KEY];
    var documentMap = map[DOCUMENT_KEY];
    if (documentMap != null) {
      this.document = new DocumentInfo.fromObject(documentMap);
    }
    var delegateMap = map[DELEGATE_KEY];
    if (delegateMap != null) {
      this.delegate = new User.fromMap(delegateMap);
    }
    // this.vote = map[VOTE_KEY];
    this.voteDecision = map[VOTE_DECISION_KEY];
  }
}