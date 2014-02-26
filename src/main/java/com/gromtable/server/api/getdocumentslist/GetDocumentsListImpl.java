package com.gromtable.server.api.getdocumentslist;

import java.util.List;

import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.hashout.HashoutList;
import com.gromtable.server.core.hashout.HashoutListToDocument;
import com.gromtable.server.core.loader.Loader;

public class GetDocumentsListImpl extends Loader<GetDocumentsListResult> {
  public GetDocumentsListImpl() {
  }

  public GetDocumentsListResult genLoad() {
    HashoutListToDocument hashoutListToDocument = new HashoutListToDocument();
    List<EntityDocument> documents = hashoutListToDocument.loadEntities(HashoutList.ALL_DOCUMENTS.getId().getKey());
    long currentTime = BaseEnvironment.getEnvironment().getTime().getNanoTime();
    for (EntityDocument document : documents) {
      if (document.isNeedDecision(currentTime)) {
        document.doDecision(currentTime);
      }
    }
    return new GetDocumentsListResult(documents);
  }
}
