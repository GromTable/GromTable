package com.gromtable.server.api.getdocumentslist;

import java.util.List;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.hashout.HashoutList;
import com.gromtable.server.core.hashout.HashoutListToDocument;
import com.gromtable.server.core.loader.Loader;

public class GetDocumentsListImpl extends Loader<GetDocumentsListResult> {
  private final Id parentId;
  public GetDocumentsListImpl(Id parentId) {
    this.parentId = parentId;
  }

  public GetDocumentsListResult genLoad() {
    HashoutListToDocument hashoutListToDocument = new HashoutListToDocument();
    Id listId = parentId;
    if (listId == null) {
      listId = HashoutList.ALL_DOCUMENTS.getId();
    }
    List<EntityDocument> documents = hashoutListToDocument.loadEntities(listId.getKey());
    long currentTime = BaseEnvironment.getEnvironment().getTime().getNanoTime();
    for (EntityDocument document : documents) {
      if (document.isNeedDecision(currentTime)) {
        document.doDecision(currentTime);
      }
    }
    return new GetDocumentsListResult(documents);
  }
}
