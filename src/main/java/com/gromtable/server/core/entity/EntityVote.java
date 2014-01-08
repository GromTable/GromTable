package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;

/***
 * [startTime; endTime)
 */
public class EntityVote extends EntityObject<EntityVote> {
  private VoteType voteType;
  private Id voterId;
  private Id choiceId;
  private long startTime;
  private long endTime = Long.MAX_VALUE;
  private VoteUserDecision voteUserDecision;
  private VoteDocumentDecision voteDocumentDecision;

  public EntityVote(VoteType voteType, Id voterId, Id choiceId, long startTime) {
    this.voteType = voteType;
    this.voterId = voterId;
    this.choiceId = choiceId;
    this.startTime = startTime;
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_VOTE;
  }

  public VoteType getVoteType() {
    return voteType;
  }

  public Id getVoterId() {
    return voterId;
  }

  public Id getChoiceId() {
    return choiceId;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long time) {
    this.endTime = time;
  }

  public VoteUserDecision getVoteUserDecision() {
    return voteUserDecision;
  }

  public void setVoteUserDecision(VoteUserDecision voteUserDecision) {
    this.voteUserDecision = voteUserDecision;
  }

  public VoteDocumentDecision getVoteDocumentDecision() {
    return voteDocumentDecision;
  }

  public void setVoteDocumentDecision(VoteDocumentDecision voteDocumentDecision) {
    this.voteDocumentDecision = voteDocumentDecision;
  }

  public boolean isActiveAt(long time) {
    return startTime <= time && time < endTime;
  }

  public static EntityVote load(final Id id) {
    return EntityObject.load(id, EntityVote.class);
  }

}
