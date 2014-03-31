import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'mainview.dart';

@CustomTag('header-view')
class HeaderView extends PolymerElement {
  HeaderView.created() : super.created() {
  } 
  
  void chooseLanguage(event, detail, target) {
    MainView.instance.chooseLanguage(event, detail, target);
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
  
  headerMessage() => Intl.message(
      "Public discussion",
      name: 'publicTable',
      args: [],
      desc: 'Public table, header on the site.',
      examples: {}); 
  
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