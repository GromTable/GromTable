import 'package:polymer/polymer.dart';
import 'viewercontext.dart';
import 'state.dart';

@CustomTag('admin-commands-view')
class AdminCommandsView extends PolymerElement {
  ViewerContext viewerContext = ViewerContext.instance;
  AdminCommandsView.created() : super.created() {
  }
  
  void goToTranslation(event, detail, target) {
    State.instance = new State(State.TRANSLATION, '');
  }
}