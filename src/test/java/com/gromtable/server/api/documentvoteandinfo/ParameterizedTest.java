package com.gromtable.server.api.documentvoteandinfo;

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
import com.gromtable.server.core.entity.EntityDocument;
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
    return data;
  }

  @Test
  public void testRun() {
    Assert.assertNotNull(testName);
    Map<String, EntityUser> users = new HashMap<String, EntityUser>();
    Map<String, EntityDocument> documents = new HashMap<String, EntityDocument>();
    ConstantTime constantTime = (ConstantTime) getTestEnvironment().getTime();
    for (String command : commands) {
      new TestCommand().execute(command, users, documents, constantTime);
    }
  }
}
