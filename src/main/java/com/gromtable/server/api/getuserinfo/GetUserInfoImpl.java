package com.gromtable.server.api.getuserinfo;

import java.util.ArrayList;
import java.util.List;

import com.gromtable.server.api.helper.VoteFilter;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityUserAndVote;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.hashout.HashoutDelegateToUser;
import com.gromtable.server.core.hashout.HashoutUserToDelegate;
import com.gromtable.server.core.loader.Loader;

public class GetUserInfoImpl extends Loader<GetUserInfoResult> {
  private Id userId;
  private boolean showVotes;
  private long votesTime;

  public GetUserInfoImpl(Id userId, boolean showVotes, long votesTime) {
    this.userId = userId;
    this.showVotes = showVotes;
    if (votesTime == 0) {
      this.votesTime = BaseEnvironment.getEnvironment().getTime().getNanoTime();
    } else {
      this.votesTime = votesTime;
    }
  }

  public GetUserInfoResult genLoad() {
    EntityUser user = EntityUser.load(userId);
    EntityUser delegate = null;
    List<EntityUser> userVotes = null;
    List<EntityUser> delegateVotes = null;

    if (showVotes) {
      userVotes = getVotes(user);
      HashoutUserToDelegate hashoutUserToDelegate = new HashoutUserToDelegate();
      List<EntityUserAndVote> userDelegateVotes =
        VoteFilter.filterVotes(hashoutUserToDelegate.loadEntities(userId.getKey()), votesTime);
      if (userDelegateVotes.size() > 1) {
        throw new GetUserInfoException("More then one delegate: " + userId);
      }
      if (userDelegateVotes.size() == 1) {
        delegate = userDelegateVotes.get(0).getUser();
      }
      if (delegate != null) {
        delegateVotes = getVotes(delegate);
      }
    }

    return new GetUserInfoResult(user, delegate, userVotes, delegateVotes);
  }

  private List<EntityUser> getVotes(EntityUser user) {
    HashoutDelegateToUser hashoutDelegateToUser = new HashoutDelegateToUser();
    List<EntityUserAndVote> userAndVotes =
      VoteFilter.filterVotes(hashoutDelegateToUser.loadEntities(user.getId().getKey()), votesTime);
    List<EntityUser> userVotes = new ArrayList<EntityUser>();
    for (EntityUserAndVote userAndVote : userAndVotes) {
      userVotes.add(userAndVote.getUser());
    }
    return userVotes;
  }
}
