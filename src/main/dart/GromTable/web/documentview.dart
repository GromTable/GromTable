import 'dart:html';
import 'dart:convert';
import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'host.dart';
import 'state.dart';
import 'document.dart';
import 'viewercontext.dart';
import 'error.dart';

@CustomTag('document-view')
class DocumentView extends PolymerElement {
  @observable bool isLoaded = false;
  @observable String error = null;
  @observable DocumentInfo document = null;
  @observable ViewerContext viewerContext = ViewerContext.instance;
  
  DocumentView.created() : super.created() {

  }
  
  enteredView() {
    startLoadingDocument(State.instance.id);
    super.enteredView();
  }
  
  void startLoadingDocument(String documentId) {
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/get_document_info',
      {
        'document_id': documentId,
        'show_votes' : true.toString(),
        'votes_time' : 0.toString(),
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
    State.instance = new State(State.USER, userId);
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
    startLoadingDocument(State.instance.id);
  }
  
  void voteYes(event, detail, target) {
    startVoteDocument(State.instance.id, 'yes');
  }
  
  void voteNo(event, detail, target) {
    startVoteDocument(State.instance.id, 'no');
  }
  
  void voteHold(event, detail, target) {
    startVoteDocument(State.instance.id, 'hold');
  }
  
  @observable
  String getState(var x, var y) {
    return x.toString() + y.toString();
  }
  
  String getHref() {
    return Host.clientHref;
  }
  
  documentHeaderMessage() => Intl.message(
      "Document number #1",
      name: 'documentHeader',
      args: [],
      desc: 'Header for document page.',
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
}