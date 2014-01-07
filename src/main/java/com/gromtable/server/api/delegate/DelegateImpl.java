package com.gromtable.server.api.delegate;

import java.util.List;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.hashout.HashoutDelegatedUserToUser;
import com.gromtable.server.core.hashout.HashoutRecord;
import com.gromtable.server.core.hashout.HashoutUserToDelegatedUser;
import com.gromtable.server.core.loader.Loader;

public class DelegateImpl extends Loader<DelegateResult> {
  private Id viewerId;
  private Id delegateTo;

  public DelegateImpl(Id viewerId, Id delegateTo) {
    this.viewerId = viewerId;
    this.delegateTo = delegateTo;
  }

  public DelegateResult genLoad() {
    try {
      HashoutUserToDelegatedUser hashoutUserToDelegatedUser = new HashoutUserToDelegatedUser();
      HashoutDelegatedUserToUser hashoutDelegatedUserToUser = new HashoutDelegatedUserToUser();
      List<HashoutRecord> currentlyDelegatedUsers = hashoutUserToDelegatedUser.loadRecords(viewerId);
      for (HashoutRecord currentlyDelegatedUser : currentlyDelegatedUsers) {
        hashoutDelegatedUserToUser.removeKey(currentlyDelegatedUser.getKey(), viewerId);
        hashoutUserToDelegatedUser.removeKey(viewerId, currentlyDelegatedUser.getKey());
      }
      hashoutUserToDelegatedUser.addKey(viewerId, delegateTo);
      hashoutDelegatedUserToUser.addKey(delegateTo, viewerId);
      return new DelegateResult(true);
    }  catch (Exception exception) {
      DelegateResult result = new DelegateResult(true);
      result.setError("Can not delegate");
      return result;
    }
  }
}
