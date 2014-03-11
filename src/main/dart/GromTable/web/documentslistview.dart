import 'dart:html';
import 'dart:convert';
import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'host.dart';
import 'state.dart';
import 'document.dart';
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
  @observable List<DocumentInfo> filteredDocuments = [];
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
    State.instance = new State(State.CHANGE_DOCUMENT, '');
    State.instance.baseDocument = basedocument;
  }
  
  void goToDocument(event, detail, target) {
    String documentId = target.attributes['data-document'];
    State.instance = new State(State.DOCUMENT, documentId);
  }
  
  bool showDocument(document) {
    var lower = searchParam.toLowerCase();
    if (document.name.toLowerCase().contains(lower)) {
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
    List<DocumentInfo> newFilteredDocument = [];
    for (var document in _allDocuments) {
      if (showDocument(document)) {
        newFilteredDocument.add(document);
      }
    }
    filteredDocuments = newFilteredDocument;
  }
  
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