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
  @observable DocumentInfo document = new DocumentInfo(null, null);
  CreateDocumentView.created() : super.created() {
  }
  void createDocument(event, detail, target) {
    startCreateDocument(document);
  }

  void startCreateDocument(DocumentInfo document) {
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/create_document'
    );
    
    Map<String, String> requestData = {'name': document.name, 'text': document.text};
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
      String documentId = map['documentId'];
      startLoadingDocument(documentId);
    }
  }
  
  void startLoadingDocument(String documentId) {
    State.instance = new State(State.DOCUMENT, documentId);
  }
  
  createDocumentHeader() => Intl.message(
      "Create document",
      name: 'createDocumentHeader',
      args: [],
      desc: 'Header on create document page.',
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
}