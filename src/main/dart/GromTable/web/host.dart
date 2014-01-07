library host;

import 'dart:html';

class Host {
  static String get origin {
    var origin = window.location.origin;
    origin = origin.replaceAll('3030', '8080');
    return origin;
  }
  static String get href {
    return window.location.href;
  }
}