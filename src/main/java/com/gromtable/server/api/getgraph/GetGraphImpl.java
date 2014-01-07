package com.gromtable.server.api.getgraph;

import java.util.ArrayList;
import java.util.List;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.hashout.HashoutDelegatedUserToUser;
import com.gromtable.server.core.hashout.HashoutUserToDelegatedUser;
import com.gromtable.server.core.loader.Loader;

public class GetGraphImpl extends Loader<GetGraphResult> {
  private Id userId;

  public GetGraphImpl(Id userId) {
    this.userId = userId;
  }

  public GetGraphResult genLoad() {
    HashoutUserToDelegatedUser hashoutUserToDelegatedUser = new HashoutUserToDelegatedUser();
    HashoutDelegatedUserToUser hashoutDelegatedUserToUser = new HashoutDelegatedUserToUser();
    EntityUser user = EntityUser.load(userId);
    EntityUser userDelegate = hashoutUserToDelegatedUser.loadEntity(userId);
    List<EntityUser> delegatedUserDelegate = new ArrayList<EntityUser>();
    if (userDelegate != null) {
      delegatedUserDelegate = hashoutDelegatedUserToUser.loadEntities(userDelegate.getId());
    }
    List<EntityUser> delegatedUser = hashoutDelegatedUserToUser.loadEntities(userId);

    return new GetGraphResult(user, userDelegate, delegatedUserDelegate, delegatedUser);
  }
}
