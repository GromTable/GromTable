import 'dart:html';
import 'dart:convert';
import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'host.dart';
import 'document.dart';
import 'state.dart';
import 'error.dart';

@CustomTag('create-document-view')
class CreateDocumentView extends PolymerElement {
  static const DRAFT_DOCUMENT_KEY = 'draftDocument';
  @published DocumentInfo basedocument = null;
  @observable DocumentInfo document = new DocumentInfo('', '');
  CreateDocumentView.created() : super.created() {
  }
  
  enteredView() {
    if (basedocument != null) {
      document = new DocumentInfo(basedocument.name, basedocument.text);

    } else {
      try {
        var savedDocument = window.localStorage[DRAFT_DOCUMENT_KEY];
        if (savedDocument != null) {
          document = new DocumentInfo.fromAutoSaveSerialization(savedDocument);
        }
      } catch (exception) {
        // In case we saved bad version somehow.
        print(exception);
        document = new DocumentInfo('', '');
        autoSave(null);
      }
    }
    new PathObserver(document, 'name').changes.listen(autoSave);
    new PathObserver(document, 'text').changes.listen(autoSave);
    super.enteredView();
  }
  
  void autoSave(var x) {
    window.localStorage[DRAFT_DOCUMENT_KEY] = document.autoSaveSerialization();
  }
  
  void createDocument(event, detail, target) {
    startCreateDocument(document);
  }
  
  void cancelDocument(event, detail, target) {
    document.name = '';
    document.text = '';
    State.instance = new State(State.DOCUMENTS_LIST, '');
  }

  void startCreateDocument(DocumentInfo document) {
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/create_document'
    );
    
    Map<String, String> requestData = {'name': document.name, 'text': document.text};
    if (basedocument != null) {
      requestData['parent_id'] = basedocument.id;
    }
    HttpRequest request = new HttpRequest();
    
    // add an event handler that is called when the request finishes
    request.onReadyStateChange.listen((_) {
      if (request.readyState == HttpRequest.DONE) {
        if (request.status == 200 || request.status == 0) {
          onCreateDocumentDone(request.responseText);
        } else {
          ErrorHandler.handleError(request.status);
        }
      }
    });

    request.open("POST", uri.toString(), async: false);
    request.withCredentials = true;
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var parts = [];
    requestData.forEach((key, value) {
      parts.add('${Uri.encodeQueryComponent(key)}='
          '${Uri.encodeQueryComponent(value)}');
    });
    var data = parts.join('&');
    request.send(data);  
  }
  
  void onCreateDocumentDone(String response) {
    Map map = JSON.decode(response);
    ErrorHandler.handleResponse(map);
    var success = map['success'];
    if (success) {
      // Clear draft document.
      document.name = '';
      document.text = '';
      String documentId = map['documentId'];
      startLoadingDocument(documentId);
    }
  }
  
  void startLoadingDocument(String documentId) {
    State.instance = new State(State.DOCUMENT, documentId);
  }
  
  @observable
  String getDocumentName(String documentName) {
    if (documentName.isEmpty) {
      return documentDefaultName();
    }
    return documentName;
  }
  
  documentDefaultName() => Intl.message(
      "New document",
      name: 'documentDefaultName',
      args: [],
      desc: 'Default name of the document.',
      examples: {});
  
  documentNameMessage() => Intl.message(
      "Document name: ",
      name: 'documentNameMessage',
      args: [],
      desc: 'Label for document name field.',
      examples: {});  
  
  documentTextMessage() => Intl.message(
      "Document text: ",
      name: 'documentTextMessage',
      args: [],
      desc: 'Label for document text field.',
      examples: {});    
  
  documentNamePlaceholder() => Intl.message(
      "Type document name here...",
      name: 'documentNamePlaceholder',
      args: [],
      desc: 'Placeholder for document name',
      examples: {});
      
  documentTextPlacehlder() => Intl.message(
      "Type document text here...",
      name: 'documentTextPlacehlder',
      args: [],
      desc: 'Placeholder for document text',
      examples: {});  
  
  createDocumentButton() => Intl.message(
    "Create document",
    name: 'createDocumentButton',
    args: [],
    desc: 'Label on create document button.',
    examples: {});
  
  cancelDocumentButton() => Intl.message(
      "Cancel",
      name: 'cancelDocumentButton',
      args: [],
      desc: 'Label on cancel document button.',
      examples: {}); 
  
  showChangesButton() => Intl.message(
    "Show changes",
    name: 'showChangesButton',
    args: [],
    desc: 'Label on show changes button during document modification.',
    examples: {});  
}