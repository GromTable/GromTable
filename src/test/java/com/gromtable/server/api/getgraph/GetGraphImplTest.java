package com.gromtable.server.api.getgraph;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityUser;

public class GetGraphImplTest extends BaseTest {
  @Test
  public void testGetEmptyGraph() {
    EntityUser user = new EntityUser("1", "User").save();

    GetGraphResult graphResult = new GetGraphImpl(user.getId()).genLoad();

    Assert.assertEquals(graphResult.getUser(), user);
    Assert.assertNull(graphResult.getUserDelegate());
    Assert.assertEquals(graphResult.getDelegatedUser().size(), 0);
    Assert.assertEquals(graphResult.getDelegatedUserDelegate().size(), 0);
  }
}
