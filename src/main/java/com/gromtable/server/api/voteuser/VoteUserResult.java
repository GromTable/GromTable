package com.gromtable.server.api.voteuser;

import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.data.Id;

public class VoteUserResult extends BaseControllerResult {
  private final boolean success;
  private final Id voteId;

  public VoteUserResult(boolean success, Id voteId) {
    this.success = success;
    this.voteId = voteId;
  }

  public Id getVoteId() {
    return voteId;
  }

  public boolean getSuccess() {
    return success;
  }
}
