package com.gromtable.server.api.votedocument;

import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.data.Id;

public class VoteDocumentResult extends BaseControllerResult {
  private final boolean success;
  private final Id voteId;

  public VoteDocumentResult(boolean success, Id voteId) {
    this.success = success;
    this.voteId = voteId;
  }

  public boolean getSuccess() {
    return success;
  }

  public Id getVoteId() {
    return voteId;
  }
}
