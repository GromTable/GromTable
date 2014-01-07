import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'viewercontext.dart';
import 'messages_all.dart';
import 'dart:html';

@CustomTag('main-view')
class MainView extends PolymerElement {
  static const SELECTED_LOCALE_KEY = 'selectedLocale';
  @observable var selectedLocale;
  @observable var publicTableMsg = null;
  static MainView mainView = null;
  var _shouldRedirect = false;
  
  @published bool isLoggedIn = ViewerContext.instance.isLoggedIn;

  MainView.created() : super.created() {
    var locale = window.localStorage[SELECTED_LOCALE_KEY];
    if (locale == null) {
      locale = 'uk';
    }

    selectedLocale = locale;
    selectedLocaleChanged();
    _shouldRedirect = false;
    mainView = this;
  }
  
  // TODO: this is clowntown
  void selectedLocaleChanged() {
    Intl.defaultLocale = selectedLocale;
    if (selectedLocale == 'en_US') {
      updateLocale(selectedLocale);
    } else {
      initializeMessages(selectedLocale).then(
          (succeeded) => updateLocale(selectedLocale));
    }
    window.localStorage[SELECTED_LOCALE_KEY] = selectedLocale;
    if (_shouldRedirect) {
      window.location.reload();
    }
    _shouldRedirect = true;
  }
  
  void updateLocale(localeName) {
    publicTableMsg = publicTable();
  }
  
  publicTable() {
    return Intl.message(
      "Public table",
      name: 'publicTable',
      args: [],
      desc: 'Public table, header on the site.',
      examples: {});
  }
}