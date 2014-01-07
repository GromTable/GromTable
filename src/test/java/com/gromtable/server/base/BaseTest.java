package com.gromtable.server.base;

import org.junit.Before;

import com.gromtable.server.core.Setup;
import com.gromtable.server.core.environment.TestEnvironment;

public class BaseTest {
  private TestEnvironment testEnvironment = null;

  @Before
  public void before() {
    testEnvironment = Setup.setTestEnvironment();
  }

  public TestEnvironment getTestEnvironment() {
    return testEnvironment;
  }
}
