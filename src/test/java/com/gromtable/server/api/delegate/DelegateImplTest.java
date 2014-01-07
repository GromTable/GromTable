package com.gromtable.server.api.delegate;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityUser;

public class DelegateImplTest extends BaseTest {
  @Test
  public void testDelegate() {
    EntityUser viewer = new EntityUser("1", "Viewer").save();
    EntityUser otherUser = new EntityUser("2", "OtherUser").save();

    DelegateResult loginResult = new DelegateImpl(viewer.getId(), otherUser.getId()).genLoad();

    Assert.assertTrue(loginResult.getSuccess());
  }
}
