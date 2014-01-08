package com.gromtable.server.api.facebookcomments;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;

public class FacebookCommentsController extends ApiController<FacebookCommentsRequest, FacebookCommentsResult> {
  public String getPath() {
    return "/facebook_comments";
  }

  public String getDescription() {
    return "Return facebook comments for url.";
  }

  public FacebookCommentsController createController() {
    return new FacebookCommentsController();
  }

  protected FacebookCommentsRequest createControllerRequest(ApiRequest apiRequest) {
    return new FacebookCommentsRequest(apiRequest);
  }

  protected FacebookCommentsResult genControllerResult(FacebookCommentsRequest request) {
    return new FacebookCommentsImpl(request.getUrl()).genLoad();
  }
}
