package com.gromtable.server.api.delegate;

import com.gromtable.server.api.core.BaseControllerResult;

public class DelegateResult extends BaseControllerResult {
  private final boolean success;

  public DelegateResult(boolean success) {
    this.success = success;
  }

  public boolean getSuccess() {
    return success;
  }
}
