package com.gromtable.server.core.viewer;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityUserSession;
import com.gromtable.server.core.entity.EntityUserSession.Status;

public class ViewerContext {
  private final EntityUserSession userSession;

  private ViewerContext(EntityUserSession userSession) {
    this.userSession = userSession;
  }


  private static ViewerContext genViewerContextFromToken(final UserSessionToken userSessionToken) {
    if (userSessionToken == null) {
      return new ViewerContext(null);
    } else {
      EntityUserSession session = EntityUserSession.load(userSessionToken.getId());
      return new ViewerContext(session);
    }
  }

  public static ViewerContext genCreateFromCookie(final String cookie) {
    UserSessionToken userSessionToken = UserSessionToken.createFromCookieToken(cookie);
    return genViewerContextFromToken(userSessionToken);
  }

  public static UserSessionToken genLogin(final Id userId) {
    EntityUserSession entityUserSession =
      new EntityUserSession(userId, EntityUserSession.Status.LOGED_IN).saveToDb(userId.getDbId());
    return new UserSessionToken(entityUserSession.getId());
  }

  public void genLogout() {
    userSession.setStatus(EntityUserSession.Status.LOGED_OUT);
    userSession.save();
  }

  public EntityUser genUser() {
    return EntityUser.load(getUserId());
  }

  public Id getUserId() {
    if (userSession != null) {
      return userSession.getUserId();
    }
    return null;
  }

  public EntityUserSession getUserSession() {
    return userSession;
  }

  public boolean isLoggedIn() {
    if (userSession != null) {
      return userSession.getStatus() == Status.LOGED_IN;
    }
    return false;
  }
}
