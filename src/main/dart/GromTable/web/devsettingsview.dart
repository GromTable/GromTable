import 'package:polymer/polymer.dart';
import 'devsettings.dart';

@CustomTag('dev-settings-view')
class DevSettingsView extends PolymerElement {
  DevSettings devSettings = DevSettings.instance;
  
  DevSettingsView.created() : super.created() {
  } 
}