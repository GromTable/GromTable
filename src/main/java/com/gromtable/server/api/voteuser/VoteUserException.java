package com.gromtable.server.api.voteuser;

public class VoteUserException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public VoteUserException(String message) {
    super(message);
  }
}