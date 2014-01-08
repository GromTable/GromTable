library document;

import 'package:polymer/polymer.dart';
import 'user.dart';

class DocumentInfo extends Observable {
  static final DOCUMENT_KEY = 'document';
  static final ID_KEY = 'id';
  static final NAME_KEY = 'name';
  static final TEXT_KEY = 'text';
  static final VOTE_BY_TIME_KEY = 'voteByTime';
  static final STATUS_KEY = 'status';
  static final AUTHOR_KEY = 'author';
  static final TOTAL_VOTES_KEY = 'totalVotes';
  static final ALL_VOTES_KEY = 'allVotes';
  static final NANO_TO_MILLI = 1000000;
  
  @observable String id;
  @observable String name;
  @observable String text;
  @observable int voteByTime;
  @observable String status;
  @observable User author;
  @observable Map<String, int> totalVotes;
  @observable var allVotes;
  
  DocumentInfo(this.name, this.text);
  
  DocumentInfo.fromMap(Map<String, Object> map) {
    var documentMap = map[DOCUMENT_KEY];
    this.name = documentMap[NAME_KEY];
    this.text = documentMap[TEXT_KEY];
    this.author = new User.fromMap(map[AUTHOR_KEY]);
    this.totalVotes = map[TOTAL_VOTES_KEY];
    this.allVotes = map[ALL_VOTES_KEY];
  }
  
  DocumentInfo.fromObject(Map<String, Object> map) {
    this.id = map[ID_KEY];
    this.name = map[NAME_KEY];
    this.text = map[TEXT_KEY];
    this.voteByTime = map[VOTE_BY_TIME_KEY];
    this.status = map[STATUS_KEY];
  }
  
  @observable
  String getVoteByString() {
    return new DateTime.fromMillisecondsSinceEpoch(voteByTime ~/ NANO_TO_MILLI).toString();
  }
}