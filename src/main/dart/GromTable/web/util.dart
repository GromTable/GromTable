library util;

import 'package:intl/intl.dart';

class Util {
  static String getTimeToString(int millis) {
    DateFormat format = new DateFormat("yyyy.MM.dd HH:mm:ss");
    return format.format(new DateTime.fromMillisecondsSinceEpoch(millis));    
  }
}