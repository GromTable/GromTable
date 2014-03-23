import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'viewercontext.dart';
import 'messages_all.dart';
import 'dart:html';
import 'state.dart';
import 'host.dart';
 
@CustomTag('main-view')
class MainView extends PolymerElement {
  static const SELECTED_LOCALE_KEY = 'selectedLocale';
  @observable var selectedLocale;
  @observable var publicTableMsg = null;
  var _shouldRedirect = false;
  
  ViewerContext viewerContext = ViewerContext.instance;
  State state = State.instance;

  MainView.created() : super.created() {
    var href = Host.clientHref;
    // Only localhost allowed to access api.
    if (href.contains("127.0.0.1")) {
      window.location.assign(href.replaceAll("127.0.0.1", "localhost"));
    }

    var locale = window.localStorage[SELECTED_LOCALE_KEY];
    if (locale == null) {
      locale = 'uk';
    }

    selectedLocale = locale;
    selectedLocaleChanged();
    _shouldRedirect = false;

  }

  // TODO: this is clowntown
  void selectedLocaleChanged() {
    Intl.defaultLocale = selectedLocale;
    initializeDateFormatting(selectedLocale, null).then((succeded) =>
    initializeMessages(selectedLocale).then((succeeded) =>
    updateLocale(selectedLocale)));
    window.localStorage[SELECTED_LOCALE_KEY] = selectedLocale;

    if (_shouldRedirect) {
      window.location.reload();
    }
    _shouldRedirect = true;
  }
  
  void updateLocale(localeName) {
  }
}