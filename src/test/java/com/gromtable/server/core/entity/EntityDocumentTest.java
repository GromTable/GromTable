package com.gromtable.server.core.entity;

import junit.framework.Assert;

import org.junit.Test;

public class EntityDocumentTest extends EntityObjectTest<EntityDocument> {
  protected EntityObject<EntityDocument> getEntityObject() {
    EntityUser creator = new EntityUser("1", "Document Creator").save();
    return new EntityDocument(23, "Document name", "Document text", 10, creator.getId());
  }

  protected Class<EntityDocument> getEntityObjectClass() {
    return EntityDocument.class;
  }

  @Test
  public void genWriteCreateDocumentTest() {
    EntityUser creator = new EntityUser("1", "Document Creator").save();
    EntityDocument savedDocument = new EntityDocument(23, "Document name", "Document text", 10, creator.getId()).save();
    EntityDocument loadedDocument = EntityDocument.load(savedDocument.getId());
    Assert.assertEquals(savedDocument.getDocumentId(), 23);
    Assert.assertEquals(loadedDocument.getDocumentId(), 23);
    Assert.assertEquals(savedDocument.getName(), "Document name");
    Assert.assertEquals(loadedDocument.getName(), "Document name");
    Assert.assertEquals(savedDocument.getText(), "Document text");
    Assert.assertEquals(loadedDocument.getText(), "Document text");
    Assert.assertEquals(savedDocument.getVoteByTime(), 10);
    Assert.assertEquals(loadedDocument.getVoteByTime(), 10);
    Assert.assertEquals(savedDocument.getCreatorId(), creator.getId());
    Assert.assertEquals(loadedDocument.getCreatorId(), creator.getId());
  }

  @Test
  public void genWriteCreateDocumentRussianTest() {
    EntityUser creator = new EntityUser("1", "Document Creator").save();
    EntityDocument savedDocument =
        new EntityDocument(23, "Имя документа", "Текст документа", 10, creator.getId()).save();
    EntityDocument loadedDocument = EntityDocument.load(savedDocument.getId());
    Assert.assertEquals(savedDocument.getName(), "Имя документа");
    Assert.assertEquals(loadedDocument.getName(), "Имя документа");
    Assert.assertEquals(savedDocument.getText(), "Текст документа");
    Assert.assertEquals(loadedDocument.getText(), "Текст документа");
    Assert.assertEquals(savedDocument.getCreatorId(), creator.getId());
    Assert.assertEquals(loadedDocument.getCreatorId(), creator.getId());
  }
}
