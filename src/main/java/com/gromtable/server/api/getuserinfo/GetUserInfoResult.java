package com.gromtable.server.api.getuserinfo;

import java.util.List;

import com.google.gson.Gson;
import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.util.EqualsUtil;

public class GetUserInfoResult extends BaseControllerResult {
  private final EntityUser user;
  private List<UserActionResult> userActions;
  private final EntityUser delegate;
  private final List<EntityUser> userVotes;

  public GetUserInfoResult(
    EntityUser user, List<UserActionResult> userActions, EntityUser delegate, List<EntityUser> userVotes) {
    this.user = user;
    this.userActions = userActions;
    this.delegate = delegate;
    this.userVotes = userVotes;
  }

  public EntityUser getUser() {
    return user;
  }

  public void setUserActions(List<UserActionResult> userActions) {
    this.userActions = userActions;
  }

  public List<UserActionResult> getUserActions() {
    return userActions;
  }

  public EntityUser getDelegate() {
    return delegate;
  }

  public List<EntityUser> getUserVotes() {
    return userVotes;
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
        && EqualsUtil.setEquals(this.userActions, other.userActions);
    }
    return false;
  }

  public String toString() {
    Gson gson = BaseEnvironment.getEnvironment().getGson();
    String json = gson.toJson(this);

    return json;
  }
}
