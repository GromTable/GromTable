package com.gromtable.server.api.settranslationinfo;

import com.gromtable.server.api.core.ApiController;
import com.gromtable.server.api.core.ApiRequest;
import com.gromtable.server.core.data.Id;

public class SetTranslationInfoController extends ApiController<SetTranslationInfoRequest, SetTranslationInfoResult> {
  public String getPath() {
    return "/set_translation_info";
  }

  public String getDescription() {
    return "Set translation info.";
  }

  public SetTranslationInfoController createController() {
    return new SetTranslationInfoController();
  }

  protected SetTranslationInfoRequest createControllerRequest(ApiRequest apiRequest) {
    return new SetTranslationInfoRequest(apiRequest);
  }

  protected SetTranslationInfoResult genControllerResult(SetTranslationInfoRequest request) {
    Id viewerId = request.getViewerContext().getUserId();
    return new SetTranslationInfoImpl(
        viewerId,
        request.getIntlMessagesSource(),
        request.getIntlMessagesUk(),
        request.getIntlMessagesRu(),
        request.getIntlMessagesEn()
    ).genLoad();
  }
}
