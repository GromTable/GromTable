package com.gromtable.server.core.entity;

import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.data.Key;
import com.gromtable.server.core.data.Pair;

public class EntityUserAndVote extends EntityObject<EntityUserAndVote> {
  public static final EntityUserAndVote EMPTY = new EntityUserAndVote();
  private EntityUser user;
  private EntityVote vote;

  private EntityUserAndVote() {
  }

  public EntityUserAndVote(EntityUser user, EntityVote vote) {
    this.user = user;
    this.vote = vote;
  }

  @SuppressWarnings("unchecked")
  public static <T extends EntityObject<T>> T load(final Key key, final Class<T> clazz) {
    Pair<Id> userAndVoteIds = splitIds(key);
    Id userId = userAndVoteIds.getFirst();
    Id voteId = userAndVoteIds.getSecond();
    EntityUser user = EntityUser.load(userId);
    EntityVote vote = EntityVote.load(voteId);
    return (T) new EntityUserAndVote(user, vote);
  }

  private static Pair<Id> splitIds(Key key) {
    // TODO: clowntown
    byte[] data = key.getRowData();
    int length = data.length / 2;
    byte[] data1 = new byte[length];
    byte[] data2 = new byte[length];
    System.arraycopy(data, 0, data1, 0, length);
    System.arraycopy(data, length + 1, data2, 0, length);
    return new Pair<Id>(Id.fromRowData(data1), Id.fromRowData(data2));
  }

  public EntityUser getUser() {
    return user;
  }

  public EntityVote getVote() {
    return vote;
  }

  public EntityUserAndVote saveToDb(final int dbId) {
    throw new IllegalStateException("Operation not allowed");
  }

  public EntityType getEntityType() {
    return EntityType.ENTITY_USER;
  }
}
