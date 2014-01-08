import 'dart:html';
import 'dart:convert';
import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'host.dart';
import 'document.dart';
import 'state.dart';

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
      Host.serverDomain,
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
          print('ERROR: ' + request.status.toString());
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
    var success = map['success'];
    if (success) {
      String documentId = map['documentId'];
      startLoadingDocument(documentId);
    } else {
      print(response);
    }
  }
  
  void startLoadingDocument(String documentId) {
    State.instance = new State(State.DOCUMENT, documentId);
  }
  
  createDocumentMessage() => Intl.message(
      "Create document",
      name: 'createDocument',
      args: [],
      desc: 'Label on create document button.',
      examples: {});
}