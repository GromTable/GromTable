package com.gromtable.server.core.entity;

import com.gromtable.server.api.getdocumentinfo.DecisionsMap;
import com.gromtable.server.api.getdocumentinfo.GetDocumentInfoImpl;
import com.gromtable.server.api.getdocumentinfo.GetDocumentInfoResult;
import com.gromtable.server.core.data.Id;

public class EntityDocument extends EntityObject<EntityDocument> {
  private String name;
  private String text;
  private long voteByTime;
  private DocumentStatus status;
  private Id creatorId;

  public EntityDocument(String name, String text, long voteByTime, Id creatorId) {
    this.name = name;
    this.text = text;
    this.voteByTime = voteByTime;
    this.status = DocumentStatus.VOTING;
    this.creatorId = creatorId;
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_DOCUMENT;
  }

  public String getName() {
    return name;
  }

  public String getText() {
    return text;
  }

  public long getVoteByTime() {
    return voteByTime;
  }

  public DocumentStatus getStatus() {
    return status;
  }

  public void setStatus(DocumentStatus status) {
    this.status = status;
  }

  public Id getCreatorId() {
    return creatorId;
  }

  public boolean isNeedDecision(long currentTime) {
    return getStatus().equals(DocumentStatus.VOTING) && getVoteByTime() < currentTime;
  }

  public static EntityDocument load(final Id id) {
    return EntityObject.load(id, EntityDocument.class);
  }

  public void doDecision(long currentTime) {
    setStatus(DocumentStatus.COUNTING);
    save();
    GetDocumentInfoResult documentInfo =
      new GetDocumentInfoImpl(getId(), true, getVoteByTime()).genLoad();
    DecisionsMap totalVotes = documentInfo.getTotalVotes();
    long yesCount = totalVotes.get(VoteDocumentDecision.YES);
    long noCount = totalVotes.get(VoteDocumentDecision.NO);
    if (yesCount > noCount) {
      setStatus(DocumentStatus.ACCEPTED);
    } else {
      setStatus(DocumentStatus.REJECTED);
    }
    save();
  }
}
