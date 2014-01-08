package com.gromtable.server.api.getuserinfo;

import java.util.List;

import com.google.gson.Gson;
import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.util.EqualsUtil;

public class GetUserInfoResult extends BaseControllerResult {
  private final EntityUser user;
  private final EntityUser delegate;
  private final List<EntityUser> userVotes;
  private final List<EntityUser> delegateVotes;

  public GetUserInfoResult(
    EntityUser user, EntityUser delegate, List<EntityUser> userVotes, List<EntityUser> delegateVotes) {
    this.user = user;
    this.delegate = delegate;
    this.userVotes = userVotes;
    this.delegateVotes = delegateVotes;
  }

  public EntityUser getUser() {
    return user;
  }

  public EntityUser getDelegate() {
    return delegate;
  }

  public List<EntityUser> getUserVotes() {
    return userVotes;
  }

  public List<EntityUser> getDelegateVotes() {
    return delegateVotes;
  }

  public int hashCode() {
    return 0;
  }

  public boolean equals(Object obj) {
    if (obj instanceof GetUserInfoResult) {
      GetUserInfoResult other = (GetUserInfoResult) obj;
      return
        EqualsUtil.equals(this.user, other.user)
        && EqualsUtil.equals(this.delegate, other.delegate)
        && EqualsUtil.setEquals(this.userVotes, other.userVotes)
        && EqualsUtil.setEquals(this.delegateVotes, other.delegateVotes);
    }
    return false;
  }

  public String toString() {
    Gson gson = BaseEnvironment.getEnvironment().getGson();
    String json = gson.toJson(this);

    return json;
  }
}
