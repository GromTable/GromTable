package com.gromtable.server.api.gettranslationinfo;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.core.data.Id;

public class GetTranslationInfoController extends ApiController<GetTranslationInfoRequest, GetTranslationInfoResult> {
  public String getPath() {
    return "/get_translation_info";
  }

  public String getDescription() {
    return "Get translation info.";
  }

  public GetTranslationInfoController createController() {
    return new GetTranslationInfoController();
  }

  protected GetTranslationInfoRequest createControllerRequest(ApiRequest apiRequest) {
    return new GetTranslationInfoRequest(apiRequest);
  }

  protected GetTranslationInfoResult genControllerResult(GetTranslationInfoRequest request) {
    Id viewerId = request.getViewerContext().getUserId();
    return new GetTranslationInfoImpl(viewerId).genLoad();
  }
}
