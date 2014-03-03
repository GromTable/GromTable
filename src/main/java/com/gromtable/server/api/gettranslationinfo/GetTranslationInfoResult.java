package com.gromtable.server.api.gettranslationinfo;

import com.gromtable.server.api.core.BaseControllerResult;

public class GetTranslationInfoResult extends BaseControllerResult {
  private final String intlMessagesSource;
  private final String intlMessagesUk;
  private final String intlMessagesRu;
  private final String intlMessagesEn;

  public GetTranslationInfoResult(
      String intlMessagesSource, String intlMessagesUk, String intlMessagesRu, String intlMessagesEn) {
    this.intlMessagesSource = intlMessagesSource;
    this.intlMessagesUk = intlMessagesUk;
    this.intlMessagesRu = intlMessagesRu;
    this.intlMessagesEn = intlMessagesEn;
  }

  public String getIntlMessagesSource() {
    return intlMessagesSource;
  }

  public String getIntlMessagesUk() {
    return intlMessagesUk;
  }

  public String getIntlMessagesRu() {
    return intlMessagesRu;
  }

  public String getIntlMessagesEn() {
    return intlMessagesEn;
  }
}
