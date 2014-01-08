package com.gromtable.server.api.getdocumentinfo;

import java.util.HashMap;
import java.util.Map;

import com.gromtable.server.core.entity.VoteDocumentDecision;

public class DecisionsMap extends HashMap<VoteDocumentDecision, Long> {
  private static final long serialVersionUID = 1L;

  public DecisionsMap() {
  }

  public DecisionsMap(VoteDocumentDecision voteDocumentDecision, long size) {
    put(voteDocumentDecision, new Long(size));
  }

  public void add(UserAndDecision userAndDecision) {
    add(new DecisionsMap(userAndDecision.getDecision(), 1));
  }

  public void subtruct(UserAndDecision userAndDecision) {
    add(new DecisionsMap(userAndDecision.getDecision(), -1));
  }

  public void add(DecisionsMap groupVotes) {
    for (Map.Entry<VoteDocumentDecision, Long> entry : groupVotes.entrySet()) {
      put(entry.getKey(), get(entry.getKey()) + entry.getValue());
    }
  }

  public Long get(VoteDocumentDecision key) {
    Long result = super.get(key);
    if (result == null) {
      result = new Long(0);
      super.put(key, result);
    }
    return result;
  }

}
