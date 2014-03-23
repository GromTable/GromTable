library host;

import 'dart:html';
import 'devsettings.dart';

class Host {
  static String get origin {
    var origin = window.location.origin;
    origin = origin.replaceAll('3030', '8080');
    return origin;
  }
  static String get clientOrigin {
    return window.location.origin;
  }
  static String get clientDomain {
    var origin = clientOrigin;
    var parts = origin.split("//");
    return parts[1];
  }
  static String get apiDomain {
    if (DevSettings.instance.isLocalApi) {
      return clientDomain.replaceAll('3030', '8080');
    } else {
     return 'gromstol.org';
    }
  }
  static String get clientHref {
    return window.location.href;
  }
  static String get hash {
    return window.location.hash;
  }
  
  static void set hash(String hash) {
    window.location.hash = hash;
  }
}