package com.gromtable.server.api.login;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.RowKey;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.hashout.HashoutLoginTokenToUser;
import com.gromtable.server.core.loader.Loader;
import com.gromtable.server.core.viewer.UserSessionToken;
import com.gromtable.server.core.viewer.ViewerContext;
import com.gromtable.server.fbapi.IHttpFetcher;

public class LoginImpl extends Loader<LoginResult> {
  public static final String CLIENT_ID_KEY = "server_facebook_client_id";
  public static final String CLIENT_SECRET_KEY = "server_facebook_client_secret";
  private String code;
  private String loginUrl;
  private String preloginUrl;

  public LoginImpl(String code, String state) {
    this.code = code;
    String[] parts = state.split("DELIMITER");
    this.loginUrl = parts[0];
    this.preloginUrl = parts[1];
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
    System.out.println(buffer.toString());
    String accessToken = data.split("[=&]")[1];
    System.out.println(accessToken);
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

  public LoginResult genLoad() {
    try {
      IHttpFetcher httpFetcher = BaseEnvironment.getEnvironment().getHttpFetcher();
      String accessToken = genAccessToken(httpFetcher);
      EntityUser user = genUser(httpFetcher, accessToken);
      LoginToken loginToken = new FbLoginToken(user.getFbId());
      Key loginTokenKey = new RowKey(loginToken.getToken());
      HashoutLoginTokenToUser hashoutLoginTokenToUser = new HashoutLoginTokenToUser();
      EntityUser savedUser = hashoutLoginTokenToUser.loadEntity(loginTokenKey);
      // We need to create new user.
      if (savedUser == null) {
        hashoutLoginTokenToUser.addEntity(loginTokenKey, user);
        savedUser = user;
      }
      UserSessionToken sessionToken = ViewerContext.genLogin(savedUser.getId());
      return new LoginResult(sessionToken.getCookieData(), preloginUrl);
    } catch (Exception exception) {
      LoginResult result = new LoginResult(null, null);
      result.setError("Can not login");
      return result;
    }
  }
}