package com.gromtable.server.api.getloggedinuser;

import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.entity.EntityUser;

public class GetLoggedinUserResult extends BaseControllerResult {
  private final EntityUser user;

  public GetLoggedinUserResult(EntityUser user) {
    this.user = user;
  }

  EntityUser getUser() {
    return user;
  }
}
