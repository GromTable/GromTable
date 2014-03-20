package com.gromtable.server.api.setuserinfo;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.core.entity.EntityUser;

public class SetUserInfoController extends ApiController<SetUserInfoRequest, SetUserInfoResult> {
  public String getPath() {
    return "/set_user_info";
  }

  public String getDescription() {
    return "Set user info.";
  }

  public SetUserInfoController createController() {
    return new SetUserInfoController();
  }

  protected SetUserInfoRequest createControllerRequest(ApiRequest apiRequest) {
    return new SetUserInfoRequest(apiRequest);
  }

  private EntityUser getUserFromRequest(SetUserInfoRequest request) {
    EntityUser user = new EntityUser();
    user.setType(request.getType());
    user.setName(request.getName());
    user.setDescription(request.getDescription());
    user.setBirthday(request.getBirthday());
    user.setCity(request.getCity());
    user.setZip(request.getZip());
    user.setPhone(request.getPhone());
    user.setFacebook(request.getFacebook());
    user.setVkontakte(request.getVkontakte());
    user.setInstagram(request.getInstagram());
    user.setTwitter(request.getTwitter());
    user.setGoogle(request.getGoogle());
    return user;
  }

  protected SetUserInfoResult genControllerResult(SetUserInfoRequest request) {
    return new SetUserInfoImpl(request.getViewerContext().getUserId(), getUserFromRequest(request)).genLoad();
  }
}
