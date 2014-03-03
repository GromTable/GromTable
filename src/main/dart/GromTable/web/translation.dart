library translation;

import 'package:polymer/polymer.dart';

class TranslationInfo extends Observable {
  static final INTL_MESSAGES_SOURCE_KEY = 'intlMessagesSource';
  static final INTL_MESSAGES_UK_KEY = 'intlMessagesUk';
  static final INTL_MESSAGES_RU_KEY = 'intlMessagesRu';
  static final INTL_MESSAGES_EN_KEY = 'intlMessagesEn';
  
  @observable String intlMessagesSource;
  @observable String intlMessagesUk;
  @observable String intlMessagesRu;
  @observable String intlMessagesEn;
  
  TranslationInfo.fromMap(Map<String, String> map) {
    this.intlMessagesSource = map[INTL_MESSAGES_SOURCE_KEY];
    this.intlMessagesUk = map[INTL_MESSAGES_UK_KEY];
    this.intlMessagesRu = map[INTL_MESSAGES_RU_KEY];
    this.intlMessagesEn = map[INTL_MESSAGES_EN_KEY];
  }
  
  Map<String, String> toMap(bool includeSource) {
    Map<String, String> map = {};
    if (includeSource) {
      map[INTL_MESSAGES_SOURCE_KEY] = intlMessagesSource;
    }
    map[INTL_MESSAGES_UK_KEY] = intlMessagesUk;
    map[INTL_MESSAGES_RU_KEY] = intlMessagesRu;
    map[INTL_MESSAGES_EN_KEY] = intlMessagesEn;
    return map;
  }
}