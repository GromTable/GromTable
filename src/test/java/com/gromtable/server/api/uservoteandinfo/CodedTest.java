package com.gromtable.server.api.uservoteandinfo;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.api.getuserinfo.GetUserInfoImpl;
import com.gromtable.server.api.getuserinfo.GetUserInfoResult;
import com.gromtable.server.api.setuserinfo.SetUserInfoImpl;
import com.gromtable.server.api.voteuser.VoteUserImpl;
import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.UserType;
import com.gromtable.server.core.entity.VoteUserDecision;
import com.gromtable.server.core.time.ConstantTime;

public class CodedTest extends BaseTest {
  @Test
  public void testSimple() {
    ConstantTime constantTime = (ConstantTime) getTestEnvironment().getTime();
    EntityUser user1 = new EntityUser("1", "Viewer").save();
    EntityUser user2 = new EntityUser("2", "Viewer").save();
    EntityUser voterType = new EntityUser();
    voterType.setType(UserType.VOTER);
    new SetUserInfoImpl(user1.getId(), voterType).genLoad();
    EntityUser delegateType = new EntityUser();
    delegateType.setType(UserType.DELEGATE);
    new SetUserInfoImpl(user2.getId(), delegateType).genLoad();

    constantTime.setNanoTime(10);
    new VoteUserImpl(user1.getId(), user2.getId(), VoteUserDecision.DELEGATE).genLoad();
    GetUserInfoResult userInfo = new GetUserInfoImpl(user1.getId(), true, 5).genLoad();
    Assert.assertEquals(userInfo.getUser().getId(), user1.getId());
    Assert.assertNull(userInfo.getDelegate());
    Assert.assertNull(userInfo.getDelegateVotes());
    userInfo = new GetUserInfoImpl(user1.getId(), true, 10).genLoad();

    Assert.assertEquals(userInfo.getUser().getId(), user1.getId());
    Assert.assertEquals(userInfo.getDelegate().getId(), user2.getId());
    Assert.assertEquals(userInfo.getDelegateVotes().size(), 1);
    Assert.assertEquals(userInfo.getDelegateVotes().get(0).getId(), user1.getId());
  }
}
