package com.gromtable.server.api.uservoteandinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.gromtable.server.api.testcommand.TestCommand;
import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.time.ConstantTime;

@RunWith(Parameterized.class)
public class ParameterizedTest extends BaseTest {
  private String testName;
  private String[] commands;

  public ParameterizedTest(String testName, String[] commands) {
    this.testName = testName;
    this.commands = commands;
  }

  @Parameters
  public static Collection<Object[]> data() {
    Collection<Object[]> data = new ArrayList<Object[]>();
    data.add(new Object[] {
      "2 users vote for one",
      new String[] {
        "set_user_info user1 voter 5",
        "set_user_info user2 delegate 5",
        "vote_user user1 user2 delegate 10",
        "get_user_info user1 true 5 user1 2 change_user_type 5 vote_user 10 0 null",
        "get_user_info user1 true 10 user1 any 0 user2",
        "set_user_info user3 voter 15",
        "vote_user user3 user2 delegate 15",
        "get_user_info user1 true 15 user1 any 0 user2",
        "get_user_info user3 true 16 user3 2 change_user_type 15 vote_user 15 0 user2",
      },
    });
    data.add(new Object[] {
      "Changing from delegate to voter kill votes",
      new String[] {
        "set_user_info user1 voter 5",
        "set_user_info user2 delegate 5",
        "set_user_info user3 voter 5",
        "vote_user user1 user2 delegate 10",
        "vote_user user3 user2 delegate 11",
        "get_user_info user1 true 5 user1 any 0 null",
        "get_user_info user1 true 10 user1 any 0 user2",
        "get_user_info user1 true 11 user1 any 0 user2",
        "get_user_info user3 true 11 user3 any 0 user2",
        "set_user_info user2 voter 15",
        "get_user_info user1 true 5 user1 any 0 null",
        "get_user_info user1 true 10 user1 any 0 user2",
        "get_user_info user1 true 14 user1 any 0 user2",
        "get_user_info user1 true 15 user1 any 0 null",
      },
    });
    data.add(new Object[] {
      "Changing from voter to delegate kill vote",
      new String[] {
        "set_user_info user1 voter 5",
        "set_user_info user2 delegate 5",
        "set_user_info user3 voter 5",
        "vote_user user1 user2 delegate 10",
        "vote_user user3 user2 delegate 11",
        "set_user_info user1 delegate 15",
        "get_user_info user1 true 5 user1 any 0 null",
        "get_user_info user1 true 10 user1 any 0 user2",
        "get_user_info user1 true 14 user1 any 0 user2",
        "get_user_info user1 true 15 user1 any 0 null",
        "get_user_info user3 true 15 user3 any 0 user2",
        "get_user_info user2 true 15 user2 any 1 user3 null",
      },
    });
    return data;
  }

  @Test
  public void testRun() {
    Assert.assertNotNull(testName);
    Map<String, EntityUser> users = new HashMap<String, EntityUser>();
    ConstantTime constantTime = (ConstantTime) getTestEnvironment().getTime();
    for (String command : commands) {
      new TestCommand().execute(command, users, null, constantTime);
    }
  }
}
