package com.gromtable.server.api.setuserinfo;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.api.getuserinfo.GetUserInfoImpl;
import com.gromtable.server.api.getuserinfo.GetUserInfoResult;
import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.UserType;

public class SetUserInfoImplTest extends BaseTest {
  @Test
  public void testSimpleSet() {
    EntityUser user = new EntityUser("1", "User").save();

    GetUserInfoResult userInfo = new GetUserInfoImpl(user.getId(), false, 0).genLoad();
    Assert.assertEquals(userInfo.getUser().getType(), UserType.VOTER);
    SetUserInfoResult setUserInfo = new SetUserInfoImpl(user.getId(), UserType.VOTER).genLoad();
    Assert.assertEquals(setUserInfo.getUser().getType(), UserType.VOTER);
    userInfo = new GetUserInfoImpl(user.getId(), false, 0).genLoad();
    Assert.assertEquals(userInfo.getUser().getType(), UserType.VOTER);
  }

  @Test
  public void testBreakDelegations() {
  }
}
