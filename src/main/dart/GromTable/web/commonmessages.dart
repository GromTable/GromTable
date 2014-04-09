library commonmessages;

import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';

class CommonMessages extends Observable {
  static userSettingsMessage() => Intl.message(
      "settings",
      name: 'userSettings',
      args: [],
      desc: 'Label on user settings button.',
      examples: {});
}