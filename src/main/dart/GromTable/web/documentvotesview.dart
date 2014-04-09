import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'user.dart';
import 'document.dart';
import 'viewercontext.dart';
import 'state.dart';

class VoteRow extends Observable {
  @observable String clazz;
  @observable String userId;
  @observable List<String> columns;
  
  VoteRow(this.clazz, this.columns, {this.userId: null});
  
  String toString() {
    return columns.toString();
  }
}

@CustomTag('document-votes-view')
class DocumentVotesView extends PolymerElement {
  @published Map<String, int> totalvotes;
  @published var allvotes;
  @published ViewerContext viewercontext = ViewerContext.instance;
  
  DocumentVotesView.created() : super.created() {

  }
  
  enteredView() {
    super.enteredView();
  }
  
  List<VoteRow> getTable(var totalVoes, var allVotes, User currentUser) {
    var decisions = [DocumentInfo.VOTE_YES, DocumentInfo.VOTE_NO, DocumentInfo.VOTE_HOLD];
    var rows = [];
    rows.add(new VoteRow("delegateHeader",
        [delegateHeaderMessage(), delegateVoteHeaderMessage(), totalVotesHeaderMessage(),
         DocumentInfo.voteYesMessage(), DocumentInfo.voteNoMessage(), DocumentInfo.voteHoldMessage()]));
    rows.add(new VoteRow("totalVotes",
        [totalRawMessage(), "", getTotalVotes(totalvotes), getVote(DocumentInfo.VOTE_YES, totalvotes),
         getVote(DocumentInfo.VOTE_NO, totalvotes), getVote(DocumentInfo.VOTE_HOLD, totalvotes)]));
    for (var delegateGroupVotes in allvotes) {
      var groupVotes = delegateGroupVotes['groupVotes'];
      var columns = [];
      var delegateVote = delegateGroupVotes['delegateVote'];
      columns.add(getUserName(delegateVote));
      columns.add(DocumentInfo.getVoteMessage(getUserDecision(delegateVote)));
      columns.add(getTotalVotes(groupVotes));
      for (var decision in decisions) {
        columns.add(getVote(decision, groupVotes));
      }
      String clazz = "delegate";
      if (currentUser != null && getUserId(delegateVote) == currentUser.id) {
        clazz += "Current";
      }
      rows.add(new VoteRow(clazz, columns, userId : getUserId(delegateVote)));
      var usersVote = delegateGroupVotes['usersVote'];
      for (var userVote in usersVote) {
        var columns = [];
        columns.add(getUserName(userVote));
        columns.add(DocumentInfo.getVoteMessage(getUserDecision(userVote)));
        columns.add(1);
        for (var decision in decisions) {
          columns.add(userVote['decision'] == decision ? 1 : 0);
        }
        String clazz = "voter";
        if (currentUser != null && getUserId(userVote) == currentUser.id) {
          clazz += "Current";
        }
        rows.add(new VoteRow(clazz, columns, userId : getUserId(userVote)));
      }
    }
    return rows;
  }
  
  delegateHeaderMessage() => Intl.message(
      "Delegate",
      name: 'delegateHeaderMessage',
      args: [],
      desc: 'Delegate column for votes table.',
      examples: {});
  
  delegateVoteHeaderMessage() => Intl.message(
      "Delegate vote",
      name: 'delegateVoteHeaderMessage',
      args: [],
      desc: 'Delegate vote column for votes table.',
      examples: {});  
  
  totalVotesHeaderMessage() => Intl.message(
      "Total votes",
      name: 'totalVotesHeaderMessage',
      args: [],
      desc: 'Total votes column for votes table.',
      examples: {});
  
  totalRawMessage() => Intl.message(
      "Total",
      name: 'totalRawMessage',
      args: [],
      desc: 'Label on raw with total votes.',
      examples: {});
  
  independentVotersMessage() => Intl.message(
      "Independent voters",
      name: 'independentVotersMessage',
      args: [],
      desc: 'Label on raw with independent voters.',
      examples: {});  

  String getUserDecision(var userVote) {
    if (userVote == null) {
      return DocumentInfo.VOTE_NA;
    }
    return userVote['decision'];
  }
  
  String getUserId(var userVote) {
    if (userVote == null) {
      return null;
    }
    return userVote['user']['id'];
  }
  
  String getUserName(var userVote) {
    if (userVote == null) {
      return independentVotersMessage();
    }
    return userVote['user']['name'];
  }
  
  int getVote(String voteDecision, Map<String, int> votes) {
    if (votes == null) {
      return 0;
    }
    if (votes.containsKey(voteDecision)) {
      return votes[voteDecision];
    } else {
      return 0;
    }
  }
  
  int getTotalVotes(Map<String, int> votes) {
    int total = 0;
    if (votes == null) {
      return 0;
    }
    votes.forEach((decision,count) => total += count);
    return total;
  }
  
  int isTrue(bool x) {
    if (x) {
      return 1;
    }
    return 0;
  }

  void rowClick(event, detail, target) {
    String userId = target.attributes['data-userid'];
    if (userId != null && userId.isNotEmpty) {
      State.instance = new State(State.USER, id: userId, time: State.instance.time);
    }
  }
  
}