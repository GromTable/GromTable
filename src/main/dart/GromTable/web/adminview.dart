import 'package:polymer/polymer.dart';
import 'dart:html';
import 'host.dart';
import 'state.dart';
import 'viewercontext.dart';
 
@CustomTag('admin-view')
class AdminView extends PolymerElement {
  ViewerContext viewerContext = ViewerContext.instance;
  @observable State state = State.instance;
  AdminView.created() : super.created() {
    var href = Host.clientHref;
    // Only localhost allowed to access api.
    if (href.contains("127.0.0.1")) {
      window.location.assign(href.replaceAll("127.0.0.1", "localhost"));
    }
  }
}