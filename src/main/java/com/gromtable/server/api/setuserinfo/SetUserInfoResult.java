package com.gromtable.server.api.setuserinfo;

import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.entity.EntityUser;

public class SetUserInfoResult extends BaseControllerResult {
  private final EntityUser user;

  public SetUserInfoResult(EntityUser user) {
    this.user = user;
  }

  public EntityUser getUser() {
    return user;
  }
}
