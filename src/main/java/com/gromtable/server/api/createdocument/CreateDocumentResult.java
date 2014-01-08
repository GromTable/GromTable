package com.gromtable.server.api.createdocument;

import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.data.Id;

public class CreateDocumentResult extends BaseControllerResult {
  private final boolean success;
  private final Id documentId;

  public CreateDocumentResult(boolean success, Id documentId) {
    this.success = success;
    this.documentId = documentId;
  }

  public boolean getSuccess() {
    return success;
  }

  public Id getDocumentId() {
    return documentId;
  }
}
