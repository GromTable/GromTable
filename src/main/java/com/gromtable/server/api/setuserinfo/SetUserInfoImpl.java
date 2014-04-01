package com.gromtable.server.api.setuserinfo;

import java.util.List;

import com.gromtable.server.api.helper.VoteFilter;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.ActionType;
import com.gromtable.server.core.entity.EntityAction;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityUserAndVote;
import com.gromtable.server.core.entity.EntityVote;
import com.gromtable.server.core.entity.UserType;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.hashout.Hashout;
import com.gromtable.server.core.hashout.HashoutDelegateToUser;
import com.gromtable.server.core.hashout.HashoutUserToAction;
import com.gromtable.server.core.hashout.HashoutUserToDelegate;
import com.gromtable.server.core.loader.Loader;

public class SetUserInfoImpl extends Loader<SetUserInfoResult> {
  private Id userId;
  private EntityUser userInfo;

  public SetUserInfoImpl(Id userId, EntityUser userInfo) {
    this.userId = userId;
    this.userInfo = userInfo;
  }

  public SetUserInfoResult genLoad() {
    EntityUser user = EntityUser.load(userId);
    UserType userType = userInfo.getType();
    if (userType != null) {
      long time = BaseEnvironment.getEnvironment().getTime().getTimeMillis();
      EntityAction newAction = new EntityAction(ActionType.CHANGE_USER_TYPE, time, userId);
      newAction.setUserType(userType);
      newAction.saveToDb(userId.getDbId());
      HashoutUserToAction hashoutUserToAction = new HashoutUserToAction();
      hashoutUserToAction.addKey(userId.getKey(), newAction.getId().getKey());
      if (user.getType().equals(UserType.DELEGATE) && userType.equals(UserType.VOTER)) {
        endVotes(new HashoutDelegateToUser(), user.getId(), time);
      }
      if (user.getType().equals(UserType.VOTER) && userType.equals(UserType.DELEGATE)) {
        endVotes(new HashoutUserToDelegate(), user.getId(), time);
      }
      user.setType(userType);
    }
    user.copyNotNullProperties(userInfo);
    user.save();
    user = EntityUser.load(userId);
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