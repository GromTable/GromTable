package com.gromtable.server.api.getdocumentslist;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.api.createdocument.CreateDocumentImpl;
import com.gromtable.server.api.createdocument.CreateDocumentResult;
import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.entity.EntityUser;

public class GetDocumentsListTest extends BaseTest {
  @Test
  public void testGetDocumentsList() {
    GetDocumentsListResult documentsList = null;
    CreateDocumentResult createDocumentResult = null;
    Set<Id> documentsIds = new HashSet<Id>();
    EntityUser creator = new EntityUser("1", "Viewer").save();
    long time = 10;

    documentsList = new GetDocumentsListImpl(null).genLoad();
    Assert.assertEquals(documentsIds, getSetIds(documentsList.getDocuments()));

    createDocumentResult = new CreateDocumentImpl(null, "X", "Y", time, creator.getId()).genLoad();
    documentsIds.add(createDocumentResult.getDocumentId());
    documentsList = new GetDocumentsListImpl(null).genLoad();
    Assert.assertEquals(documentsIds, getSetIds(documentsList.getDocuments()));

    createDocumentResult = new CreateDocumentImpl(null, "X", "Y", time, creator.getId()).genLoad();
    documentsIds.add(createDocumentResult.getDocumentId());
    documentsList = new GetDocumentsListImpl(null).genLoad();
    Assert.assertEquals(documentsIds, getSetIds(documentsList.getDocuments()));
  }

  private Set<Id> getSetIds(List<EntityDocument> documents) {
    Set<Id> ids = new HashSet<Id>();
    for (EntityDocument document : documents) {
      ids.add(document.getId());
    }
    return ids;
  }
}
