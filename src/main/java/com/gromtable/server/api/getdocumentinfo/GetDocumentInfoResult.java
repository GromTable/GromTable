package com.gromtable.server.api.getdocumentinfo;

import java.util.List;

import com.gromtable.server.api.core.BaseControllerResult;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.entity.EntityUser;

public class GetDocumentInfoResult extends BaseControllerResult {
  private final long currentTime;
  private final EntityDocument document;
  private final EntityUser author;
  private DecisionsMap totalVotes;
  private List<DelegateGroupVotes> allVotes;

  public GetDocumentInfoResult(long currentTime, EntityDocument document, EntityUser author) {
    this.currentTime = currentTime;
    this.document = document;
    this.author = author;
  }

  public long getCurrentTime() {
    return currentTime;
  }

  public EntityDocument getDocument() {
    return document;
  }

  public EntityUser getAuthor() {
    return author;
  }

  public void setVotes(DecisionsMap totalVotes, List<DelegateGroupVotes> allVotes) {
    this.totalVotes = totalVotes;
    this.allVotes = allVotes;
  }

  public DecisionsMap getTotalVotes() {
    return totalVotes;
  }

  public List<DelegateGroupVotes> getAllVotes() {
    return allVotes;
  }
}
