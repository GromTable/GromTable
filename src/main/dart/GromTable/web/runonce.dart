library RunOnce;

import 'dart:async';

class RunOnce { 
  bool _scheduled = false;
  
  void schedule(computation()) {
    if (!_scheduled) {
      _scheduled = true;
      new Future(() {
        _scheduled = false;
        computation();
      });
    }
  } 
}