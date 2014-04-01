package com.gromtable.server.api.getuserinfo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.util.EqualsUtil;

public class GetUserInfoResultTest extends BaseTest {
  @Test
  public void equalsTest() {
    EntityUser user1 = new EntityUser("1", "User");
    EntityUser user2 = new EntityUser("2", "User");
    List<EntityUser> list1 = new ArrayList<EntityUser>();
    List<EntityUser> list2 = new ArrayList<EntityUser>();
    list2.add(user2);
    list2.add(user1);
    List<EntityUser> list3 = new ArrayList<EntityUser>();
    list3.add(user1);
    list3.add(user2);
    Assert.assertTrue(EqualsUtil.setEquals(list2, list3));
    List<UserActionResult> userActions = new ArrayList<UserActionResult>();

    GetUserInfoResult result1 = new GetUserInfoResult(user1, userActions, user2, list2);
    GetUserInfoResult result2 = new GetUserInfoResult(user1, userActions, user2, list2);
    GetUserInfoResult result3 = new GetUserInfoResult(user1, userActions, user2, list1);
    GetUserInfoResult result4 = new GetUserInfoResult(user1, userActions, user2, list3);
    Assert.assertTrue(result1.equals(result2));
    Assert.assertTrue(result1.equals(result4));
    Assert.assertFalse(result1.equals(result3));
  }
}
