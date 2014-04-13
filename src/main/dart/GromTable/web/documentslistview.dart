import 'dart:html';
import 'dart:convert';
import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'host.dart';
import 'state.dart';
import 'document.dart';
import 'documentitem.dart';
import 'error.dart';
import 'viewercontext.dart';

@CustomTag('documents-list-view')
class DocumentView extends PolymerElement {
  @published DocumentInfo basedocument = null;
  List<DocumentInfo> _allDocuments = [];
  @observable String searchParam = '';
  @observable bool showVoting = true;
  @observable bool showAccepted = true;
  @observable bool showRejected = false;
  @observable bool showCancelled = false;
  @observable List<DocumentItem> filteredDocuments = [];
  @observable ViewerContext viewerContext = ViewerContext.instance;
  
  DocumentView.created() : super.created() {
  }
  
  enteredView() {
    startLoadingDocumentsList();
    super.enteredView();
    onPropertyChange(this, #searchParam, search);
    onPropertyChange(this, #showVoting, search);
    onPropertyChange(this, #showAccepted, search);
    onPropertyChange(this, #showRejected, search);
    onPropertyChange(this, #showCancelled, search);
  }
  
  void searchEnter(Event e, var detail, var target) {
    int code = (e as KeyboardEvent).keyCode;
    if (code == 13) {
      if (filteredDocuments.length == 1) {
        State.instance = new State(State.DOCUMENT, id: filteredDocuments[0].id);        
      }
    }
  }
  
  void startLoadingDocumentsList() {
    Map<String, String> requestData = {};
    if (basedocument != null) {
      requestData['parent_id'] = basedocument.id;
    }
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/get_documents_list',
      requestData
    );

    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
        .then(onDocumentsListLoaded)
        .catchError((var error) {
          ErrorHandler.handleError(error);
         });
  }
  
  void onDocumentsListLoaded(String response) {
    Map result = JSON.decode(response);
    ErrorHandler.handleResponse(result);
    List documentsList = result['documents'];
    List<DocumentInfo> newDocuments = [];
    for (var documentMap in documentsList) {
      newDocuments.add(new DocumentInfo.fromObject(documentMap));
    }
    _allDocuments = newDocuments;
    search();
  }

  void proposeChanges(event, detail, target) {
    State.instance = new State(State.CHANGE_DOCUMENT);
    State.instance.baseDocument = basedocument;
  }
  
  void goToDocument(event, detail, target) {
    String documentId = target.attributes['data-document'];
    State.instance = new State(State.DOCUMENT, id: documentId);
  }
  
  bool showDocument(document) {
    var lower = searchParam.toLowerCase();
    if (document.getNameWithDocumentId().toLowerCase().contains(lower)) {
      switch (document.status) {
        case 'VOTING':
          return showVoting;
        case 'ACCEPTED':
          return showAccepted;
        case 'REJECTED':
          return showRejected;
        case 'CANCELLED':
          return showCancelled;
        default:
          return false;
      }
    }
    return false;
  }
  
  void search() {
    List<DocumentItem> newFilteredDocument = [];
    var lower = searchParam.toLowerCase();
    for (DocumentInfo document in _allDocuments) {
      if (showDocument(document)) {
        var documentName = document.getNameWithDocumentId().toLowerCase();
        var searchIndex = documentName.indexOf(lower);
        var beforeName = documentName.substring(0, searchIndex);
        var selectedName = documentName.substring(searchIndex, searchIndex + lower.length);
        var afterName = documentName.substring(searchIndex + lower.length);
        
        newFilteredDocument.add(new DocumentItem(
          document.id, documentName, document.getVoteDecisionMessage(), document.getVoteByString(),
          beforeName, selectedName, afterName
        ));
      }
    }
    filteredDocuments = newFilteredDocument;
  }
  
  searchPlaceholder() => Intl.message(
      "Search by name",
      name: 'searchPlaceholder',
      args: [],
      desc: 'Placeholder for document search.',
      examples: {});
  
  documentListMessage() => Intl.message(
      "Document list",
      name: 'documentListHeader',
      args: [],
      desc: 'Header for document list section.',
      examples: {});
  
  showVotingMessage() => Intl.message(
      "Show voting",
      name: 'showVotingOption',
      args: [],
      desc: 'Option for showing documents in voting state.',
      examples: {});
  
  showAcceptedMessage() => Intl.message(
      "Show accepted",
      name: 'showAcceptedOption',
      args: [],
      desc: 'Option for showing documents in accepted state.',
      examples: {});
  
  showRejectedMessage() => Intl.message(
      "Show rejected",
      name: 'showRejectedOption',
      args: [],
      desc: 'Option for showing documents in rejected state.',
      examples: {});
  
  showCancelledMessage() => Intl.message(
      "Show cancelled",
      name: 'showCancelled',
      args: [],
      desc: 'Option for showing documents in cancelled state.',
      examples: {});
  
  goToDocumentButton() => Intl.message(
      "Go to document",
      name: 'goToDocumentButton',
      args: [],
      desc: 'Go to document button.',
      examples: {});
  
  
  proposeChangesButtonMessage() => Intl.message(
      "Propouse changes",
      name: 'propouseChangesButton',
      args: [],
      desc: 'Text on the button for proposing changes for the document.',
      examples: {});
}