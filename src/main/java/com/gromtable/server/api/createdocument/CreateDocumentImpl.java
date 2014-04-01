package com.gromtable.server.api.createdocument;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.ActionType;
import com.gromtable.server.core.entity.EntityAction;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.environment.BaseEnvironment;
import com.gromtable.server.core.environment.Environment;
import com.gromtable.server.core.hashout.HashoutList;
import com.gromtable.server.core.hashout.HashoutListToDocument;
import com.gromtable.server.core.hashout.HashoutUserToAction;
import com.gromtable.server.core.loader.Loader;

public class CreateDocumentImpl extends Loader<CreateDocumentResult> {
  private Id parentId;
  private String name;
  private String text;
  private long voteByTime;
  private Id creatorId;

  public CreateDocumentImpl(Id parentId, String name, String text, long voteByTime, Id creatorId) {
    this.parentId = parentId;
    this.name = name;
    this.text = text;
    this.voteByTime = voteByTime;
    this.creatorId = creatorId;
  }

  public CreateDocumentResult genLoad() {
    Environment environment = BaseEnvironment.getEnvironment();
    EntityDocument document = new EntityDocument(name, text, voteByTime, creatorId);
    document.saveToDb(creatorId.getDbId());
    long time = environment.getTime().getTimeMillis();
    HashoutListToDocument hashoutListToDocument = new HashoutListToDocument();
    HashoutUserToAction hashoutUserToAction = new HashoutUserToAction();
    Id listId = parentId;
    ActionType actionType = ActionType.CHANGE_DOCUMENT;
    if (listId == null) {
      listId = HashoutList.ALL_DOCUMENTS.getId();
      actionType = ActionType.CREATE_DOCUMENT;
    }
    EntityAction newAction = new EntityAction(actionType, time, creatorId);
    newAction.setDocumentId(document.getId());
    newAction.saveToDb(creatorId.getDbId());
    hashoutListToDocument.addKey(listId.getKey(), document.getId().getKey());
    hashoutUserToAction.addKey(creatorId.getKey(), newAction.getId().getKey());
    return new CreateDocumentResult(true, document.getId());
  }
}
