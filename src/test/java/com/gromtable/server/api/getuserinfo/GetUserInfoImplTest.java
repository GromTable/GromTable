package com.gromtable.server.api.getuserinfo;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityUser;

public class GetUserInfoImplTest extends BaseTest {
  @Test
  public void testGetEmptyGraph() {
    EntityUser user = new EntityUser("1", "User").save();

    GetUserInfoResult graphResult = new GetUserInfoImpl(user.getId(), true, 10).genLoad();

    Assert.assertEquals(graphResult.getUser(), user);
    Assert.assertNull(graphResult.getDelegate());
    Assert.assertNull(graphResult.getDelegateVotes());
  }
}
