library messages_ru;
import 'package:intl/intl.dart';
import 'package:intl/message_lookup_by_library.dart';

final messages = new MessageLookup();

class MessageLookup extends MessageLookupByLibrary {

  get localeName => 'ru';

  final messages = {
    "login" : () => Intl.message("Зайдите на сайт с помощю:"),
    "publicTable" : () => Intl.message("Громадский стол"),
  };
}