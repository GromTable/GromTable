package com.gromtable.server.core.viewer;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.EntityUserSession;
import com.gromtable.server.core.entity.EntityUserSession.Status;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.hashout.HashoutTokenToUserSession;

public class ViewerContext {
  private final EntityUserSession userSession;

  private ViewerContext(EntityUserSession userSession) {
    this.userSession = userSession;
  }


  private static ViewerContext genViewerContextFromToken(final UserSessionToken userSessionToken) {
    if (userSessionToken == null) {
      return new ViewerContext(null);
    } else {
      HashoutTokenToUserSession hashoutTokenToUserSession = new HashoutTokenToUserSession();
      EntityUserSession session = hashoutTokenToUserSession.loadEntity(userSessionToken.getKeyData());
      return new ViewerContext(session);
    }
  }

  public static ViewerContext genCreateFromCookie(final String cookie) {
    UserSessionToken userSessionToken = UserSessionToken.createFromCookieToken(cookie);
    return genViewerContextFromToken(userSessionToken);
  }

  public static UserSessionToken genLogin(final Id userId) {
    HashoutTokenToUserSession hashoutTokenToUserSession = new HashoutTokenToUserSession();
    UserSessionToken token = UserSessionToken.create(userId, BaseEnvironment.getEnvironment());
    EntityUserSession entityUserSession =
      new EntityUserSession(userId, EntityUserSession.Status.LOGED_IN);
    hashoutTokenToUserSession.addEntity(token.getKeyData(), entityUserSession, userId.getDbId());
    return token;
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
