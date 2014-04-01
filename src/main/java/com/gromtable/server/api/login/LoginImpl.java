package com.gromtable.server.api.login;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.entity.ActionType;
import com.gromtable.server.core.entity.EntityAction;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.ErrorType;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.Environment;
import com.gromtable.server.core.hashout.HashoutLoginTokenToUser;
import com.gromtable.server.core.hashout.HashoutUserToAction;
import com.gromtable.server.core.loader.Loader;
import com.gromtable.server.core.viewer.UserSessionToken;
import com.gromtable.server.core.viewer.ViewerContext;
import com.gromtable.server.fbapi.IHttpFetcher;

public class LoginImpl extends Loader<LoginResult> {
  public static final String CLIENT_ID_KEY = "server_facebook_client_id";
  public static final String CLIENT_SECRET_KEY = "server_facebook_client_secret";
  public static final String FACEBOOK = "facebook";
  private static final String TEST = "test";

  private String type;
  private String code;
  private String loginUrl;
  private String preloginUrl;

  public LoginImpl(String type, String code, String state) {
    this.type = type;
    this.code = code;
    String[] parts = state.split("DELIMITER");
    if (parts.length == 2) {
      this.loginUrl = parts[0];
      this.preloginUrl = parts[1];
    }
  }

  private String genAccessToken(IHttpFetcher httpFetcher) {
    Map<String, String> serverSettings = BaseEnvironment.getEnvironment().getSettings().getServerSettings();
    String clientId = serverSettings.get(CLIENT_ID_KEY);
    String clientSecret = serverSettings.get(CLIENT_SECRET_KEY);
    String redirectUri = loginUrl;

    StringBuffer buffer = new StringBuffer();
    buffer.append("https://graph.facebook.com/oauth/access_token");
    buffer.append("?client_id=" + clientId);
    buffer.append("&client_secret=" + clientSecret);
    buffer.append("&redirect_uri=" + redirectUri);
    buffer.append("&code=" + code);

    String data = httpFetcher.genContent(buffer.toString());
    String accessToken = data.split("[=&]")[1];
    return accessToken;
  }

  private EntityUser genUser(IHttpFetcher httpFetcher, String accessToken) {
    StringBuffer buffer = new StringBuffer();
    buffer.append("https://graph.facebook.com/me");
    buffer.append("?access_token=" + accessToken);
    String response = httpFetcher.genContent(buffer.toString());
    JsonParser parser = new JsonParser();
    JsonElement json = parser.parse(response);
    JsonObject object = json.getAsJsonObject();
    String id = object.get("id").getAsString();
    String name = object.get("name").getAsString();
    return new EntityUser(id, name);
  }

  private LoginResult loginViaFacebook() {
    IHttpFetcher httpFetcher = BaseEnvironment.getEnvironment().getHttpFetcher();
    String accessToken = genAccessToken(httpFetcher);
    EntityUser user = genUser(httpFetcher, accessToken);
    LoginToken loginToken = new FbLoginToken(user.getFbId());
    Key loginTokenKey = new Key(loginToken.getToken());
    return saveUser(user, loginTokenKey);
  }

  private LoginResult saveUser(EntityUser user, Key loginTokenKey) {
    Environment environment = BaseEnvironment.getEnvironment();
    long time = environment.getTime().getTimeMillis();
    HashoutLoginTokenToUser hashoutLoginTokenToUser = new HashoutLoginTokenToUser();
    EntityUser savedUser = hashoutLoginTokenToUser.loadEntity(loginTokenKey);
    // We need to create new user.
    if (savedUser == null) {
      hashoutLoginTokenToUser.addEntity(loginTokenKey, user);
      savedUser = user;
      HashoutUserToAction hashoutUserToAction = new HashoutUserToAction();
      EntityAction newAction = new EntityAction(ActionType.USER_JOINED, time, user.getId());
      newAction.saveToDb(user.getId().getDbId());
      hashoutUserToAction.addKey(user.getId().getKey(), newAction.getId().getKey());
    }
    UserSessionToken sessionToken = ViewerContext.genLogin(savedUser.getId());
    return new LoginResult(sessionToken.getCookieData(), preloginUrl);
  }

  private EntityUser parseUser(String code) {
    return new EntityUser(null, code);
  }

  private LoginResult loginViaTestUsers() {
    EntityUser user = parseUser(code);
    LoginToken loginToken = new TestLoginToken(user.getTestId());
    Key loginTokenKey = new Key(loginToken.getToken());
    return saveUser(user, loginTokenKey);
  }

  public LoginResult genLoad() {
    try {
      if (type.equals(FACEBOOK)) {
        return loginViaFacebook();
      } else if (type.equals(TEST)) {
        return loginViaTestUsers();
      } else {
        throw new RuntimeException("Unknown type.");
      }
    } catch (Exception exception) {
      LoginResult result = new LoginResult(null, null);
      result.setError(ErrorType.UKNOWN_ERROR);
      exception.printStackTrace();
      return result;
    }
  }
}