package com.gromtable.server.api.getloggedinuser;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.loader.Loader;

public class GetLoggedinUserImpl extends Loader<GetLoggedinUserResult> {
  private Id userId;

  public GetLoggedinUserImpl(Id userId) {
    this.userId = userId;
  }

  public GetLoggedinUserResult genLoad() {
    EntityUser user = EntityUser.load(userId);
    return new GetLoggedinUserResult(user);
  }
}
