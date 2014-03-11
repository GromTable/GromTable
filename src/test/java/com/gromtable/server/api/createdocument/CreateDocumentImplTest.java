package com.gromtable.server.api.createdocument;

import org.junit.Assert;
import org.junit.Test;

import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityUser;

public class CreateDocumentImplTest extends BaseTest {
  @Test
  public void testCreateDocument() {
    EntityUser creator = new EntityUser("1", "Viewer").save();
    long time = 10;
    CreateDocumentResult createDocumentResult =
        new CreateDocumentImpl(null, "Document name", "Document text", time, creator.getId()).genLoad();

    Assert.assertTrue(createDocumentResult.getSuccess());
  }
}
