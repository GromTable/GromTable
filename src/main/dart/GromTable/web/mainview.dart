import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'package:intl/date_symbol_data_local.dart';
import 'messages_all.dart';
import 'dart:html';
import 'dart:async';
import 'state.dart';
import 'host.dart';
import 'devsettings.dart';
 
@CustomTag('main-view')
class MainView extends PolymerElement {
  static const SELECTED_LOCALE_KEY = 'selectedLocale';
  @observable bool isInitialized = false;
  
  State state = State.instance;
  DevSettings devSettings = DevSettings.instance;

  MainView.created() : super.created() {
    var href = Host.clientHref;
    // Only localhost allowed to access api.
    if (href.contains("127.0.0.1")) {
      window.location.assign(href.replaceAll("127.0.0.1", "localhost"));
    }

    changeSelectedLocale(getSelectedLocale());
  }
  
  withLocale(String locale) {
    switch (locale) {
      case 'uk':
        return Intl.withLocale(locale, () => ukr());
      case 'ru':
        return Intl.withLocale(locale, () => rus());
      case 'en':
        return Intl.withLocale(locale, () => en());
    }
  }
  
  String getSelectedLocale() {
    var locale = window.localStorage[SELECTED_LOCALE_KEY];
    if (locale == null) {
      locale = 'uk';
    }
    return locale;
  }
  
  void chooseLanguage(event, detail, target) {
    changeSelectedLocale(target.attributes['data-lang']);
  }

  void changeSelectedLocale(String selectedLocale) {
    Intl.defaultLocale = selectedLocale;
    if (isInitialized) {
      isInitialized = false;
      Timer.run(() => isInitialized = true);
    } else {
      initializeDateFormatting('uk', null).then((succeded) =>
      initializeMessages('uk').then((succeeded) =>
      initializeDateFormatting('ru', null).then((succeded) =>
      initializeMessages('ru').then((succeeded) =>
      initializeDateFormatting('en', null).then((succeded) =>
      initializeMessages('en').then((succeeded) =>
      Timer.run(() => isInitialized = true)))))));
    }
    window.localStorage[SELECTED_LOCALE_KEY] = selectedLocale;
  }
  
  String ukr() => Intl.message(
      "ukr",
      name: 'ukr',
      args: [],
      desc: 'Ukrainian language',
      examples: {});
  
  String rus() => Intl.message(
      "rus",
      name: 'rus',
      args: [],
      desc: 'Russian language',
      examples: {});
  
  String en() => Intl.message(
      "en",
      name: 'en',
      args: [],
      desc: 'English language',
      examples: {});
}