package com.gromtable.server.core.viewer;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.gromtable.server.core.Setup;
import com.gromtable.server.core.entity.EntityUser;

public class ViewerContextTest {
  @Before
  public void before() {
    Setup.setTestEnvironment();
  }

  @Test
  public void testGenViewerContextFromToken() {
    EntityUser user = new EntityUser("1", "Name").save();
    UserSessionToken token = new UserSessionToken(user.getId());

    ViewerContext unknownViewerContext = ViewerContext.genCreateFromCookie(token.getCookieData());
    Assert.assertNull(unknownViewerContext.getUserId());

    ViewerContext nullViewerContext = ViewerContext.genCreateFromCookie("unknown_cookie");
    Assert.assertNull(nullViewerContext.getUserId());
  }

  @Test
  public void testGenLogin() {
    EntityUser user = new EntityUser("1", "Name").save();
    UserSessionToken token = ViewerContext.genLogin(user.getId());
    ViewerContext viewerContext = ViewerContext.genCreateFromCookie(token.getCookieData());
    Assert.assertEquals(user.getId(), viewerContext.getUserId());
    Assert.assertEquals(user.getId().getDbId(), viewerContext.getUserSession().getId().getDbId());
  }

  @Test
  public void testGenLogout() {
    EntityUser user = new EntityUser("1", "Name").save();
    UserSessionToken token = ViewerContext.genLogin(user.getId());
    ViewerContext viewerContext = ViewerContext.genCreateFromCookie(token.getCookieData());
    Assert.assertEquals(user.getId(), viewerContext.getUserId());

    viewerContext.genLogout();
    ViewerContext logoutViewerContext = ViewerContext.genCreateFromCookie(token.getCookieData());
    Assert.assertNull(logoutViewerContext.getUserId());
  }
}