library host;

import 'dart:html';

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
     return 'gromstol.org';
     // Uncomment for local api server
     // return clientDomain.replaceAll('3030', '8080');
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