package com.gromtable.server.api.gettranslationinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityJson;
import com.gromtable.server.core.hashout.HashoutList;
import com.gromtable.server.core.hashout.HashoutListToJson;
import com.gromtable.server.core.loader.Loader;

public class GetTranslationInfoImpl extends Loader<GetTranslationInfoResult> {
  private Id viewerId;

  public GetTranslationInfoImpl(Id viewerId) {
    this.viewerId = viewerId;
  }

  public GetTranslationInfoResult genLoad() {
    checkPermission(viewerId);
    HashoutListToJson hashoutListToJson = new HashoutListToJson();
    List<EntityJson> jsons = hashoutListToJson.loadEntities(HashoutList.INTL_MESSAGES_CURRENT.getId().getKey());
    if (jsons.size() != 4) {
      throw new GeTranslationInfoException("There should be exactly 4 json texts");
    }
    Map<String, String> jsonTexts = new HashMap<String, String>();
    for (EntityJson json : jsons) {
      jsonTexts.put(json.getName(), json.getValue());
    }

    return new GetTranslationInfoResult(
      getText(jsonTexts, IntlMessagesType.INTL_MESSAGES_SOURCE),
      getText(jsonTexts, IntlMessagesType.INTL_MESSAGES_UK),
      getText(jsonTexts, IntlMessagesType.INTL_MESSAGES_RU),
      getText(jsonTexts, IntlMessagesType.INTL_MESSAGES_EN)
    );
  }

  private String getText(Map<String, String> jsonTexts, IntlMessagesType intlMessagesType) {
    String typeName = intlMessagesType.name();
    if (jsonTexts.containsKey(typeName)) {
      return jsonTexts.get(typeName);
    }
    throw new GeTranslationInfoException("Intl messages type not found: " + typeName);
  }

  private void checkPermission(Id viewerId) {
    // TODO: Implement permission manager.
  }
}
