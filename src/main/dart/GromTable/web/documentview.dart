import 'dart:html';
import 'dart:convert';
import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'host.dart';
import 'state.dart';
import 'document.dart';
import 'viewercontext.dart';
import 'error.dart';
import 'runonce.dart';

@CustomTag('document-view')
class DocumentView extends PolymerElement {
  RunOnce runOnce = new RunOnce();
  @published String documentid;
  @published int time;
  @observable bool isLoaded = false;
  @observable String error = null;
  @observable DocumentInfo document = null;
  @observable ViewerContext viewerContext = ViewerContext.instance;
  
  DocumentView.created() : super.created() {
  }
  
  enteredView() {
    // TODO: Why atrributeChanged not called normally?
    onPropertyChange(this, #time, () => attributeChanged('timeX', '', ''));
    super.enteredView();
  }
  
  attributeChanged(String name, String oldValue, String newValue) {
    runOnce.schedule(() => startLoadingDocument(documentid, time));
    super.attributeChanged(name, oldValue, newValue);
  }
  
  void startLoadingDocument(String documentId, int votesTime) {
    var votes_time = "0";
    if (votesTime  != null) {
      votes_time = votesTime.toString();
    }
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/get_document_info',
      {
        'document_id': documentId,
        'show_votes' : true.toString(),
        'votes_time' : votes_time,
      }
    );
    var request = HttpRequest.getString(uri.toString())
        .then(onDocumentLoaded)
        .catchError((var error) {
          ErrorHandler.handleError(error);
         });
  }
  
  void onDocumentLoaded(String response) {
    Map map = JSON.decode(response);
    ErrorHandler.handleResponse(map);
    document = new DocumentInfo.fromMap(map);
    isLoaded = true;
  }
  
  void authorClick(userId) {
    State.instance = new State(State.USER, id: userId);
  }
  
  void startVoteDocument(String documentId, String voteDecision) {
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/vote_document',
      {
        'document_id': documentId,
        'vote_decision' : voteDecision,
      }
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
        .then(onVoteDocumentDone)
        .catchError((var error) {
          ErrorHandler.handleError(error);
         });
  }
  
  void onVoteDocumentDone(String response) {
    Map map = JSON.decode(response);
    ErrorHandler.handleResponse(map);
    startLoadingDocument(documentid, time);
  }
  
  void voteYes(event, detail, target) {
    startVoteDocument(documentid, 'yes');
  }
  
  void voteNo(event, detail, target) {
    startVoteDocument(documentid, 'no');
  }
  
  void voteHold(event, detail, target) {
    startVoteDocument(documentid, 'hold');
  }
  
  void documentChanges(event, detail, target) {
    State.instance = new State(State.DOCUMENT_CHANGES);
    State.instance.baseDocument = document;
  }
  
  void endResults(event, detail, target) {
    State.instance = new State(State.DOCUMENT, id: document.id, time: document.voteByTime);
  }
  
  void currentResults(event, detail, target) {
    var newState = new State(State.DOCUMENT, id: document.id);
    State.instance = newState;    
  }
  
  @observable
  String getState(var x, var y) {
    return x.toString() + y.toString();
  }
  
  String getHref() {
    return Host.clientHref;
  }
  
  documentHeaderMessage(documentId) => Intl.message(
      "Document number $documentId",
      name: 'documentHeader',
      args: [documentId],
      desc: 'Header for document page.',
      examples: {'documentId' : 1});
  
  documentAuthorMessage() => Intl.message(
      "Author of the document:",
      name: 'documentAuthorMessage',
      args: [],
      desc: 'Author of the document.',
      examples: {});
  
  documentVoteYesButtonMessage() => Intl.message(
      "Vote Yes",
      name: 'documentVoteYesButton',
      args: [],
      desc: 'Text on the button voting yes on the document',
      examples: {});
  
  documentVoteNoButtonMessage() => Intl.message(
      "Vote No",
      name: 'documentVoteNoButton',
      args: [],
      desc: 'Text on the button voting no on the document',
      examples: {});
  
  documentVoteHoldButtonMessage() => Intl.message(
      "Vote Hold",
      name: 'documentVoteHoldButton',
      args: [],
      desc: 'Text on the button voting hold on the document',
      examples: {});
  
  documentChangesButtonMessage() => Intl.message(
      "Document changes",
      name: 'documentChangesButton',
      args: [],
      desc: 'Text on the button to see proposed document changes.',
      examples: {});
  
  documentEndResultsMessage() => Intl.message(
      "Show end of vote results.",
      name: 'endResultsButton',
      args: [],
      desc: 'Text on button to see result in the end of the vote.',
      examples: {});
  
  documentCurrentResultsMessage() => Intl.message(
      "Show current results.",
      name: 'currentResultsButton',
      args: [],
      desc: 'Text on button to see result in the end of the vote.',
      examples: {});
  
  
  voteEndedMessage(voteDecision) => Intl.message(
      "Vote on the document ended with decision: $voteDecision",
      name: 'voteEndedMessage',
      args: [voteDecision],
      desc: 'Message with result of the vote for document.',
      examples: {'voteDecision' : 'ACCEPTED'});
}