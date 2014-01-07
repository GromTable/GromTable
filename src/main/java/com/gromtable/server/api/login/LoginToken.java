package com.gromtable.server.api.login;

/***
 * This object represent unique token by which we can find user.
 * Example: fb:fb_used_id, email:email@example.com, vk:vk_user_id
 */
public interface LoginToken {
  String getToken();
}
