import 'package:polymer/polymer.dart';
import 'user.dart';
import 'viewercontext.dart';

class VoteRow extends Observable {
  @observable String clazz;
  @observable List<String> columns;
  
  VoteRow(this.clazz, this.columns);
  
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
    var decisions = ['YES', 'NO', 'HOLD'];
    var rows = [];
    rows.add(new VoteRow("delegateHeader", ["DELEGATE", "DELEGATE VOTE", "TOTAL VOTES", "YES", "NO", "HOLD"]));
    rows.add(new VoteRow("totalVotes", ["TOTAL", "", getTotalVotes(totalvotes), getVote('YES', totalvotes), getVote('NO', totalvotes), getVote('HOLD', totalvotes)]));
    for (var delegateGroupVotes in allvotes) {
      var groupVotes = delegateGroupVotes['groupVotes'];
      var columns = [];
      var delegateVote = delegateGroupVotes['delegateVote'];
      columns.add(getUserName(delegateVote));
      columns.add(getUserDecision(delegateVote));
      columns.add(getTotalVotes(groupVotes));
      for (var decision in decisions) {
        columns.add(getVote(decision, groupVotes));
      }
      String clazz = "delegate";
      if (currentUser != null && getUserId(delegateVote) == currentUser.id) {
        clazz += "Current";
      }
      rows.add(new VoteRow(clazz, columns));
      var usersVote = delegateGroupVotes['usersVote'];
      for (var userVote in usersVote) {
        var columns = [];
        columns.add(getUserName(userVote));
        columns.add(getUserDecision(userVote));
        columns.add(1);
        for (var decision in decisions) {
          columns.add(userVote['decision'] == decision ? 1 : 0);
        }
        String clazz = "voter";
        if (currentUser != null && getUserId(userVote) == currentUser.id) {
          clazz += "Current";
        }
        rows.add(new VoteRow(clazz, columns));
      }
    }
    return rows;
  }
  
  String getUserDecision(var userVote) {
    if (userVote == null) {
      return 'NA';
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
      return 'Independent voters';
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
  
  attributeChanged(String name, String oldValue, String newValue) {
    print('attributeChanged ' + name);
    print(totalvotes);
    print(allvotes);
    super.attributeChanged(name, oldValue, newValue);
  }
}