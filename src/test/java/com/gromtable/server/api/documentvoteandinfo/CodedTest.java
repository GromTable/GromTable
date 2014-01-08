package com.gromtable.server.api.documentvoteandinfo;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;

import com.gromtable.server.api.getdocumentinfo.DelegateGroupVotes;
import com.gromtable.server.api.getdocumentinfo.GetDocumentInfoImpl;
import com.gromtable.server.api.getdocumentinfo.GetDocumentInfoResult;
import com.gromtable.server.api.setuserinfo.SetUserInfoImpl;
import com.gromtable.server.api.votedocument.VoteDocumentImpl;
import com.gromtable.server.api.voteuser.VoteUserImpl;
import com.gromtable.server.base.BaseTest;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.UserType;
import com.gromtable.server.core.entity.VoteDocumentDecision;
import com.gromtable.server.core.entity.VoteUserDecision;
import com.gromtable.server.core.time.ConstantTime;

public class CodedTest extends BaseTest {
  @Test
  public void testSimple() {
    ConstantTime constantTime = (ConstantTime) getTestEnvironment().getTime();
    EntityUser user1 = new EntityUser("1", "Viewer").save();
    EntityUser user2 = new EntityUser("2", "Viewer").save();
    EntityUser user3 = new EntityUser("3", "Viewer").save();
    EntityDocument document = new EntityDocument("Document", "Document", 20, user1.getId()).save();
    constantTime.setNanoTime(5);
    new SetUserInfoImpl(user1.getId(), UserType.VOTER).genLoad();
    new SetUserInfoImpl(user2.getId(), UserType.DELEGATE).genLoad();
    new SetUserInfoImpl(user3.getId(), UserType.VOTER).genLoad();

    constantTime.setNanoTime(10);
    new VoteUserImpl(user1.getId(), user2.getId(), VoteUserDecision.DELEGATE).genLoad();
    new VoteUserImpl(user3.getId(), user2.getId(), VoteUserDecision.DELEGATE).genLoad();

    constantTime.setNanoTime(15);
    new VoteDocumentImpl(user2.getId(), document.getId(), VoteDocumentDecision.YES).genLoad();
    constantTime.setNanoTime(16);
    new VoteDocumentImpl(user1.getId(), document.getId(), VoteDocumentDecision.NO).genLoad();
    constantTime.setNanoTime(17);
    new VoteDocumentImpl(user3.getId(), document.getId(), VoteDocumentDecision.HOLD).genLoad();

    GetDocumentInfoResult result = new GetDocumentInfoImpl(document.getId(), true, 15).genLoad();
    Map<VoteDocumentDecision, Long> totalVotes = result.getTotalVotes();
    Assert.assertEquals(totalVotes.size(), 1);
    Assert.assertEquals(totalVotes.get(VoteDocumentDecision.YES).longValue(), 3);
    List<DelegateGroupVotes> allVotes = result.getAllVotes();
    Assert.assertEquals(allVotes.size(), 1);
    DelegateGroupVotes groupVotes = allVotes.get(0);
    Assert.assertEquals(groupVotes.getDelegateVote().getDecision(), VoteDocumentDecision.YES);
    Assert.assertEquals(groupVotes.getUsersVote().size(), 0);
    Assert.assertEquals(groupVotes.getGroupVotes(), totalVotes);

    result = new GetDocumentInfoImpl(document.getId(), true, 16).genLoad();
    totalVotes = result.getTotalVotes();
    Assert.assertEquals(totalVotes.size(), 2);
    Assert.assertEquals(totalVotes.get(VoteDocumentDecision.YES).longValue(), 2);
    Assert.assertEquals(totalVotes.get(VoteDocumentDecision.NO).longValue(), 1);
    allVotes = result.getAllVotes();
    Assert.assertEquals(allVotes.size(), 1);
    groupVotes = allVotes.get(0);
    Assert.assertEquals(groupVotes.getDelegateVote().getDecision(), VoteDocumentDecision.YES);
    Assert.assertEquals(groupVotes.getUsersVote().size(), 1);
    Assert.assertEquals(groupVotes.getGroupVotes(), totalVotes);
  }
}
