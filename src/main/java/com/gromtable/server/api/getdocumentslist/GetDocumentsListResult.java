package com.gromtable.server.api.getdocumentslist;

import java.util.List;

import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.entity.EntityDocument;

public class GetDocumentsListResult extends BaseControllerResult {
  private final List<EntityDocument> documents;

  public GetDocumentsListResult(List<EntityDocument> documents) {
    this.documents = documents;
  }

  public List<EntityDocument> getDocuments() {
    return documents;
  }
}
