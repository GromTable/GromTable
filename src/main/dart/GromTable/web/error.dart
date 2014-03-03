library error;

import 'viewercontext.dart';

class ErrorHandler {
  static const String ERROR = 'error';
  static const String NOT_LOGGED_IN = 'NOT_LOGGED_IN';
  static bool handleResponse(Map map) {
    String error = map[ERROR];
    switch (error) {
      case NOT_LOGGED_IN:
        ViewerContext.instance.logout();
        return false;
    }
    return true;
  }
  static void handleError(var error) {
    print(error);
  }
}