package com.gromtable.server.api.gettranslationinfo;

public enum IntlMessagesType {
  INTL_MESSAGES_SOURCE("intlMessagesSource"),
  INTL_MESSAGES_RU("intlMessagesRu"),
  INTL_MESSAGES_UK("intlMessagesUk"),
  INTL_MESSAGES_EN("intlMessagesEn");

  private final String name;

  IntlMessagesType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
