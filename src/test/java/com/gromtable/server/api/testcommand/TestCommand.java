package com.gromtable.server.api.testcommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import junit.framework.Assert;

import com.gromtable.server.api.getdocumentinfo.GetDocumentInfoResult;
import com.gromtable.server.api.getuserinfo.GetUserInfoImpl;
import com.gromtable.server.api.getuserinfo.GetUserInfoResult;
import com.gromtable.server.api.setuserinfo.SetUserInfoImpl;
import com.gromtable.server.api.voteuser.VoteUserImpl;
import com.gromtable.server.core.data.Id;
import com.gromtable.server.core.entity.EntityDocument;
import com.gromtable.server.core.entity.EntityUser;
import com.gromtable.server.core.entity.UserType;
import com.gromtable.server.core.entity.VoteDocumentDecision;
import com.gromtable.server.core.entity.VoteUserDecision;
import com.gromtable.server.core.time.ConstantTime;

public class TestCommand {
  private static final String SET_USER_INFO = "set_user_info";
  private static final String GET_USER_INFO = "get_user_info";
  private static final String VOTE_USER = "vote_user";
  private static final String VOTE_DOCUMENT = "vote_document";
  private static final String GET_DOCUMENT_INFO = "get_document_info";
  private static final String NULL = "null";
  private StringTokenizer st;
  private Map<String, EntityUser> users;
  private Map<String, EntityDocument> documents;
  private EntityUser user;
  private UserType userType;
  private long time;
  private EntityUser voter;
  private EntityUser delegate;
  private VoteUserDecision voteUserDecision;
  private boolean showVotes;
  private long votesTime;
  private EntityDocument document;
  private VoteDocumentDecision voteDocumentDecision;
  private GetUserInfoResult userResult;
  private GetDocumentInfoResult documentResult;

  private GetDocumentInfoResult readDocumentResult() {
    // TODO Auto-generated method stub
    return null;
  }

  private EntityDocument getDocument(Map<String, EntityDocument> documents, String document) {
    EntityDocument savedDocument = documents.get(document);
    if (savedDocument == null) {
      EntityUser author = new EntityUser("" + documents.size(), "Author").save();
      savedDocument = new EntityDocument(document, document, 100, author.getId());
      savedDocument.save();
      documents.put(document, savedDocument);
    }
    return savedDocument;
  }

  private GetUserInfoResult readUserResult() {
    EntityUser resultUser = getUser(st.nextToken());
    List<EntityUser> userVotes = readList();
    EntityUser resultDelegate = getUser(st.nextToken());
    List<EntityUser> delegateVotes = readList();
    return new GetUserInfoResult(resultUser, resultDelegate, userVotes, delegateVotes);
  }

  private List<EntityUser> readList() {
    String number = st.nextToken();
    if (number.equals(NULL)) {
      return null;
    }
    long length = Long.parseLong(number);
    List<EntityUser> list = new ArrayList<EntityUser>();
    for (int i = 0; i < length; i++) {
      list.add(getUser(st.nextToken()));
    }
    return list;
  }

  public void execute(
      String line,
      Map<String, EntityUser> users,
      Map<String, EntityDocument> documents,
      ConstantTime constantTime) {
    this.st = new StringTokenizer(line);
    this.users = users;
    this.documents = documents;

    String commandName = st.nextToken();
    if (commandName.equals(SET_USER_INFO)) {
      user = getUser(st.nextToken());
      userType = UserType.valueOf(st.nextToken().toUpperCase());
      time = Long.parseLong(st.nextToken());

      constantTime.setNanoTime(time);
      EntityUser newUserType = new EntityUser();
      newUserType.setType(userType);
      new SetUserInfoImpl(user.getId(), newUserType).genLoad();
    } else if (commandName.equals(VOTE_USER)) {
      voter = getUser(st.nextToken());
      delegate = getUser(st.nextToken());
      voteUserDecision = VoteUserDecision.valueOf(st.nextToken().toUpperCase());
      time = Long.parseLong(st.nextToken());

      Id voterId = voter.getId();
      Id delegateId = delegate.getId();
      constantTime.setNanoTime(time);
      new VoteUserImpl(voterId, delegateId, voteUserDecision).genLoad();
    } else if (commandName.equals(GET_USER_INFO)) {
      user = getUser(st.nextToken());
      showVotes = Boolean.parseBoolean(st.nextToken());
      votesTime = Long.parseLong(st.nextToken());
      userResult = readUserResult();

      Id userId = user.getId();
      GetUserInfoResult result = new GetUserInfoImpl(userId, showVotes, votesTime).genLoad();
      Assert.assertEquals(userResult, result);
    } else if (commandName.equals(VOTE_DOCUMENT)) {
      voter = getUser(st.nextToken());
      document = getDocument(documents, st.nextToken());
      voteDocumentDecision = VoteDocumentDecision.valueOf(st.nextToken().toUpperCase());
    } else if (commandName.equals(GET_DOCUMENT_INFO)) {
      document = getDocument(documents, st.nextToken());
      showVotes = Boolean.parseBoolean(st.nextToken());
      votesTime = Long.parseLong(st.nextToken());
      documentResult = readDocumentResult();
    }
  }

  private EntityUser getUser(String user) {
    if (user.equals(NULL)) {
      return null;
    }
    EntityUser savedUser = users.get(user);
    if (savedUser == null) {
      savedUser = new EntityUser("" + users.size(), user);
      savedUser.save();
      users.put(user, savedUser);
    } else {
      savedUser = EntityUser.load(savedUser.getId());
    }
    return savedUser;
  }
}
