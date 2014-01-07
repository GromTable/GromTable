package com.gromtable.server.api.getgraph;

import java.util.List;

import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.entity.EntityUser;

public class GetGraphResult extends BaseControllerResult {
  private final EntityUser user;
  private final EntityUser userDelegate;
  private final List<EntityUser> delegatedUserDelegate;
  private final List<EntityUser> delegatedUser;

  public GetGraphResult(
      EntityUser user, EntityUser userDelegate,
      List<EntityUser> delegatedUserDelegate, List<EntityUser> delegatedUser) {
    this.user = user;
    this.userDelegate = userDelegate;
    this.delegatedUserDelegate = delegatedUserDelegate;
    this.delegatedUser = delegatedUser;
  }

  EntityUser getUser() {
    return user;
  }

  EntityUser getUserDelegate() {
    return userDelegate;
  }

  List<EntityUser> getDelegatedUserDelegate() {
    return delegatedUserDelegate;
  }

  List<EntityUser> getDelegatedUser() {
    return delegatedUser;
  }
}
