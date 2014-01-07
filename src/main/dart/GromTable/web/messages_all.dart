library messages_all;

import 'dart:async';
import 'package:intl/message_lookup_by_library.dart';
import 'package:intl/src/intl_helpers.dart';
import 'messages_uk.dart' as uk;
import 'messages_ru.dart' as ru;
import 'package:intl/intl.dart';

// TODO: Use lazy loading of the requested library.
MessageLookupByLibrary _findExact(localeName) {
  switch (localeName) {
    case 'uk' : return uk.messages;
    case 'ru' : return ru.messages;
    default: return null;
  }
}

initializeMessages(localeName) {
  initializeInternalMessageLookup(() => new CompositeMessageLookup());
  messageLookup.addLocale(localeName, _findGeneratedMessagesFor);
  return new Future.value();
}

MessageLookupByLibrary _findGeneratedMessagesFor(locale) {
  var actualLocale = Intl.verifiedLocale(locale, (x) => _findExact(x) != null);
  if (actualLocale == null) return null;
  return _findExact(actualLocale);
}