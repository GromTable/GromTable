package com.gromtable.server.api.voteuser;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.UserType;
import com.gromtable.server.core.entity.VoteUserDecision;

public class VoteUserImplTest extends BaseTest {
  @Test
  public void testDelegate() {
    EntityUser viewer = new EntityUser("1", "Viewer");
    viewer.setType(UserType.VOTER);
    viewer.save();
    EntityUser otherUser = new EntityUser("2", "OtherUser");
    otherUser.setType(UserType.DELEGATE);
    otherUser.save();
    VoteUserDecision decision = VoteUserDecision.DELEGATE;

    VoteUserResult loginResult = new VoteUserImpl(viewer.getId(), otherUser.getId(), decision).genLoad();

    Assert.assertTrue(loginResult.getSuccess());
  }
}
