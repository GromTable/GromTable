library document;

import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'dart:convert';
import 'user.dart';

// TODO: refactor this to be more close to java version.
class DocumentInfo extends Observable {
  static const VOTE_YES = 'YES';
  static const VOTE_NO = 'NO';
  static const VOTE_HOLD = 'HOLD';
  static const VOTE_NA = 'NA';
  
  static const VOTING = 'VOTING';
  static const ACCEPTED = 'ACCEPTED';
  static const REJECTED = 'REJECTED';
  static const CANCELED = 'CANCELED';
  
  static final CURRENT_TIME_KEY = 'currentTime';
  static final DOCUMENT_KEY = 'document';
  static final ID_KEY = 'id';
  static final DOCUMENT_ID_KEY = 'documentId';
  static final NAME_KEY = 'name';
  static final TEXT_KEY = 'text';
  static final VOTE_BY_TIME_KEY = 'voteByTime';
  static final STATUS_KEY = 'status';
  static final AUTHOR_KEY = 'author';
  static final TOTAL_VOTES_KEY = 'totalVotes';
  static final ALL_VOTES_KEY = 'allVotes';
  
  @observable String id;
  @observable int documentId;
  @observable String name;
  @observable String text;
  @observable int currentTime;
  @observable int voteByTime;
  @observable String status;
  @observable User author;
  @observable Map<String, int> totalVotes;
  @observable var allVotes;
  
  DocumentInfo(this.name, this.text);
  
  DocumentInfo.fromMap(Map<String, Object> map) {
    this.currentTime = map[CURRENT_TIME_KEY];
    var documentMap = map[DOCUMENT_KEY];
    this.id = documentMap[ID_KEY];
    this.documentId = documentMap[DOCUMENT_ID_KEY];
    this.name = documentMap[NAME_KEY];
    this.text = documentMap[TEXT_KEY];
    this.status = documentMap[STATUS_KEY];
    this.voteByTime = documentMap[VOTE_BY_TIME_KEY];
    this.author = new User.fromMap(map[AUTHOR_KEY]);
    this.totalVotes = map[TOTAL_VOTES_KEY];
    this.allVotes = map[ALL_VOTES_KEY];
  }
  
  DocumentInfo.fromObject(Map<String, Object> map) {
    this.id = map[ID_KEY];
    this.documentId = map[DOCUMENT_ID_KEY];
    this.name = map[NAME_KEY];
    this.text = map[TEXT_KEY];
    this.voteByTime = map[VOTE_BY_TIME_KEY];
    this.status = map[STATUS_KEY];
  }
  
  factory DocumentInfo.fromAutoSaveSerialization(String json) {
    return new DocumentInfo.fromObject(JSON.decode(json));
  }
  
  String autoSaveSerialization() {
    return JSON.encode({NAME_KEY: name, TEXT_KEY: text});
  }
  
  @observable
  String getVoteByString() {
    DateFormat format = new DateFormat("[yyyy.MM.dd / HH:mm]");
    return format.format(new DateTime.fromMillisecondsSinceEpoch(voteByTime));
  }
  
  String getNameWithDocumentId() {
    return name + "(" + getDocumentId() + ")";
  }
  
  @observable
  String getDocumentId() {
    var res = documentId.toString();
    while (res.length < 4) {
      res = "0" + res;
    }
    return res;
  }
  
  @observable
  static String getVoteMessage(String vote) {
    switch (vote) {
      case VOTE_YES:
        return voteYesMessage();
      case VOTE_NO:
        return voteNoMessage();
      case VOTE_HOLD:
        return voteHoldMessage();
      case VOTE_NA:
        return voteNAMessage();
      default:
        throw new Exception("Unknown vote $vote");
    }
  }
  
  @observable
  String getVoteDecisionMessage() {
    switch (status) {
      case VOTING:
        return votingMessage();
      case ACCEPTED:
        return acceptedMessage();
      case REJECTED:
        return rejectedMessage();
      case CANCELED:
        return canceledMessage();
      default:
        throw new Exception("Unknown status: $status");
    }
  }

  static voteYesMessage() => Intl.message(
      "Yes",
      name: 'voteYes',
      args: [],
      desc: 'Yes vote on some document.',
      examples: {});
  
  static voteNoMessage() => Intl.message(
      "No",
      name: 'voteNo',
      args: [],
      desc: 'No vote on some document.',
      examples: {});
  
  static voteHoldMessage() => Intl.message(
      "Hold",
      name: 'voteHold',
      args: [],
      desc: 'Hold vote on some document.',
      examples: {});
  
  static voteNAMessage() => Intl.message(
      "NA",
      name: 'voteNA',
      args: [],
      desc: 'Case when vote decision is not applicable.',
      examples: {});
  
  votingMessage() => Intl.message(
      "Votting",
      name: 'votingMessage',
      args: [],
      desc: 'Document status at some point of time.',
      examples: {});
  
  acceptedMessage() => Intl.message(
      "Accepted",
      name: 'acceptedMessage',
      args: [],
      desc: 'Document status at some point of time.',
      examples: {});  
  
  rejectedMessage() => Intl.message(
      "Rejected",
      name: 'rejectedMessage',
      args: [],
      desc: 'Document status at some point of time.',
      examples: {}); 
   
  canceledMessage() => Intl.message(
      "Canceled",
      name: 'canceledMessage',
      args: [],
      desc: 'Document status at some point of time.',
      examples: {}); 
  
  @observable
  bool showEndOfVoteResults() {
    return this.voteByTime < this.currentTime;
  }
}