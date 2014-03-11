import 'package:polymer/polymer.dart';
import 'dart:core';
import 'dart:async';
import 'document.dart';
import 'documentsdiff.dart';

class DocumentPart extends Observable {
  @observable String text;
  @observable String command;
  DocumentPart(this.text, this.command);
}

@CustomTag('documents-diff-view')
class DocumentsDiffView extends PolymerElement {
  static const Duration DELAY = const Duration(seconds: 1);
  Timer currentTimer = null;
  PathObserver observer = null;
  
  @published DocumentInfo basedocument = null;
  @published DocumentInfo newdocument = null;
  @observable List<DocumentPart> editScript = null;
  
  DocumentsDiffView.created() : super.created() {
  }
  
  enteredView() {
    observer = new PathObserver(newdocument, 'text');
    observer.changes.listen(onTextUpdate);
    super.enteredView();
  }
  
  List<DocumentPart> wrapDiffParts(List<DiffPart> diffParts) {
    List<DocumentPart> parts = [];
    for (var diffPart in diffParts) {
      parts.add(new DocumentPart(diffPart.text, diffPart.command));
    }
    return parts;
  }
  
  void onTextUpdate(var x) {
    if (currentTimer != null) {
      currentTimer.cancel();
      currentTimer = null;
    }
    currentTimer = new Timer(DELAY, updateEditScript);
  }
  
  void updateEditScript() {
    if (basedocument != null && newdocument != null && basedocument.text != null && newdocument.text != null) {
      editScript = getEditScript(basedocument.text, newdocument.text);
    }
  }
  
  List<DocumentPart> getEditScript(String text1, String text2) {
    return wrapDiffParts(new DocumentsDiff(text1, text2, 20).getEditScript());
  }
  
  @observable
  List<String> splitLines(String text) {
    return text.split("\n");
  }
}