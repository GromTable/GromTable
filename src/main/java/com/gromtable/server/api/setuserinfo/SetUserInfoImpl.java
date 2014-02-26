package com.gromtable.server.api.setuserinfo;

import java.util.List;

import com.gromtable.server.api.helper.VoteFilter;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityUserAndVote;
import com.gromtable.server.core.entity.EntityVote;
import com.gromtable.server.core.entity.UserType;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.hashout.Hashout;
import com.gromtable.server.core.hashout.HashoutDelegateToUser;
import com.gromtable.server.core.hashout.HashoutUserToDelegate;
import com.gromtable.server.core.loader.Loader;

public class SetUserInfoImpl extends Loader<SetUserInfoResult> {
  private Id userId;
  private UserType userType;

  public SetUserInfoImpl(Id userId, UserType userType) {
    this.userId = userId;
    this.userType = userType;
  }

  public SetUserInfoResult genLoad() {
    EntityUser user = EntityUser.load(userId);
    if (userType != null) {
      long time = BaseEnvironment.getEnvironment().getTime().getNanoTime();
      if (user.getType().equals(UserType.DELEGATE) && userType.equals(UserType.VOTER)) {
        endVotes(new HashoutDelegateToUser(), user.getId(), time);
      }
      if (user.getType().equals(UserType.VOTER) && userType.equals(UserType.DELEGATE)) {
        endVotes(new HashoutUserToDelegate(), user.getId(), time);
      }
      user.setType(userType);
      user.save();
      user = EntityUser.load(userId);
    }
    return new SetUserInfoResult(user);
  }

  private void endVotes(Hashout<EntityUserAndVote> hashout, Id userId, long time) {
    List<EntityUserAndVote> userVotes =
      VoteFilter.filterVotes(hashout.loadEntities(userId.getKey()), time);
    for (EntityUserAndVote userVote : userVotes) {
      EntityVote vote = userVote.getVote();
      vote.setEndTime(time);
      vote.save();
    }
  }
}