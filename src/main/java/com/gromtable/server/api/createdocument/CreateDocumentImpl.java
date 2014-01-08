package com.gromtable.server.api.createdocument;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.hashout.HashoutList;
import com.gromtable.server.core.hashout.HashoutListToDocument;
import com.gromtable.server.core.loader.Loader;

public class CreateDocumentImpl extends Loader<CreateDocumentResult> {
  private String name;
  private String text;
  private long voteByTime;
  private Id creatorId;

  public CreateDocumentImpl(String name, String text, long voteByTime, Id creatorId) {
    this.name = name;
    this.text = text;
    this.voteByTime = voteByTime;
    this.creatorId = creatorId;
  }

  public CreateDocumentResult genLoad() {
    EntityDocument document = new EntityDocument(name, text, voteByTime, creatorId);
    document.save();
    HashoutListToDocument hashoutListToDocument = new HashoutListToDocument();
    hashoutListToDocument.addKey(HashoutList.ALL_DOCUMENTS.getId(), document.getId());
    return new CreateDocumentResult(true, document.getId());
  }
}
