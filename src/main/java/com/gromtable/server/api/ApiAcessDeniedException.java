package com.gromtable.server.api;

public class ApiAcessDeniedException extends Exception {
  private static final long serialVersionUID = 1L;

  public ApiAcessDeniedException(String message) {
    super(message);
  }
}
