package com.gromtable.server.api.settranslationinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gromtable.server.api.gettranslationinfo.IntlMessagesType;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.entity.EntityJson;
import com.gromtable.server.core.hashout.HashoutList;
import com.gromtable.server.core.hashout.HashoutListToJson;
import com.gromtable.server.core.loader.Loader;

public class SetTranslationInfoImpl extends Loader<SetTranslationInfoResult> {
  private final Id viewerId;
  private final String intlMessagesSource;
  private final String intlMessagesUk;
  private final String intlMessagesRu;
  private final String intlMessagesEn;

  public SetTranslationInfoImpl(Id viewerId, String intlMessagesSource,
      String intlMessagesUk, String intlMessagesRu, String intlMessagesEn) {
    this.viewerId = viewerId;
    this.intlMessagesSource = intlMessagesSource;
    this.intlMessagesUk = intlMessagesUk;
    this.intlMessagesRu = intlMessagesRu;
    this.intlMessagesEn = intlMessagesEn;
  }

  public SetTranslationInfoResult genLoad() {
    checkPermission(viewerId);
    HashoutListToJson hashoutListToJson = new HashoutListToJson();
    List<EntityJson> jsons = hashoutListToJson.loadEntities(HashoutList.INTL_MESSAGES_CURRENT.getId().getKey());
    Map<String, List<EntityJson>> nameToJson = new HashMap<String, List<EntityJson>>();
    for (EntityJson json : jsons) {
      String name = json.getName();
      if (!nameToJson.containsKey(name)) {
        nameToJson.put(name, new ArrayList<EntityJson>());
      }
      nameToJson.get(name).add(json);
    }
    if (intlMessagesSource != null) {
      saveNewJson(hashoutListToJson, IntlMessagesType.INTL_MESSAGES_SOURCE, nameToJson, intlMessagesSource);
    }
    if (intlMessagesUk != null) {
      saveNewJson(hashoutListToJson, IntlMessagesType.INTL_MESSAGES_UK, nameToJson, intlMessagesUk);
    }
    if (intlMessagesRu != null) {
      saveNewJson(hashoutListToJson, IntlMessagesType.INTL_MESSAGES_RU, nameToJson, intlMessagesRu);
    }
    if (intlMessagesEn != null) {
      saveNewJson(hashoutListToJson, IntlMessagesType.INTL_MESSAGES_EN, nameToJson, intlMessagesEn);
    }
    return new SetTranslationInfoResult();
  }

  private void saveNewJson(
      HashoutListToJson hashoutListToJson,
      IntlMessagesType intlMessagesType,
      Map<String, List<EntityJson>> nameToJson,
      String intMessagesText) {
    String typeName = intlMessagesType.name();
    Key fromKey = HashoutList.INTL_MESSAGES_CURRENT.getId().getKey();
    EntityJson json = new EntityJson(typeName, intMessagesText);
    hashoutListToJson.addEntity(fromKey, json);
    List<EntityJson> previousJsons = nameToJson.get(typeName);
    for (EntityJson previousJson : previousJsons) {
      hashoutListToJson.removeKey(fromKey, previousJson.getId().getKey());
    }
  }

  private void checkPermission(Id viewerId) {
    // TODO Implement permission manager.
  }
}