library error;

import 'viewercontext.dart';

class ErrorHandler {
  static const String ERROR = 'error';
  static const String NOT_LOGGED_IN = 'NOT_LOGGED_IN';
  static void handleResponse(Map map) {
    String error = map[ERROR];
    switch (error) {
      case NOT_LOGGED_IN:
        ViewerContext.instance.logout();
        break;
    }
  }
  static void handleError(var error) {
    print(error);
  }
}