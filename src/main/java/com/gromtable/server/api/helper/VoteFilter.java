package com.gromtable.server.api.helper;

import java.util.ArrayList;
import java.util.List;

import com.gromtable.server.core.entity.EntityUserAndVote;

public class VoteFilter {
  private VoteFilter() {
  }

  public static List<EntityUserAndVote> filterVotes(List<EntityUserAndVote> userAndVotes, long voteTime) {
    List<EntityUserAndVote> activeVotes = new ArrayList<EntityUserAndVote>();
    for (EntityUserAndVote userAndVote : userAndVotes) {
      if (userAndVote.getVote().isActiveAt(voteTime)) {
        activeVotes.add(userAndVote);
      }
    }
    return activeVotes;
  }
}
