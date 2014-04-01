import 'package:intl/intl.dart';
import 'package:polymer/polymer.dart';
import 'useraction.dart';
import 'util.dart';

@CustomTag('user-action-view')
class UserActionView extends PolymerElement {
  @published UserAction action;

  UserActionView.created() : super.created() {
  }
  
  @observable
  String getTimeToString(int millis) {
    return Util.getTimeToString(millis);
  }
    
  userJoinedStoryMessage(actorName) => Intl.message(
      "$actorName joined.",
      name: 'userJoinedStory',
      args: [actorName],
      desc: 'Story about joining the site.',
      examples: {'actorName' : 'Roman'});
    
  createDocumentStoryMessage(actorName, documentName) => Intl.message(
      "$actorName created '$documentName' document.",
      name: 'userDocumentStory',
      args: [actorName],
      desc: 'Story about creating a document.',
      examples: {'actorName' : 'Roman', 'documentName' : 'Simple name'});
  
  changeDocumentStoryMessage(actorName, documentName) => Intl.message(
    "$actorName changed '$documentName' document.",
    name: 'userDocumentStory',
    args: [actorName],
    desc: 'Story about changing a document.',
    examples: {'actorName' : 'Roman', 'documentName' : 'Simple name'});

  voteUserStoryMessage(actorName, delegateName) => Intl.message(
      "$actorName delegated $delegateName.",
      name: 'voteUserStory',
      args: [actorName, delegateName],
      desc: 'Story about delegating someone.',
      examples: {'actorName' : 'Roman', 'delegateName': 'Ivan'});

  voteDocumentStoryMessage(actorName, documentName, voteDecision) => Intl.message(
      "$actorName voted on the '$documentName' with decision: $voteDecision",
      name: 'voteDocumentStory',
      args: [documentName, voteDecision],
      desc: 'Story about voting on the document.',
      examples: {'actorName' : 'Roman', 'documentName': 'Simple document', 'voteDecision' : 'YES'});
  
  becameDelegateStoryMessage(actorName) => Intl.message(
      "$actorName became delegate",
      name: 'becameDelegateStory',
      args: [actorName],
      desc: 'Story about becaming a delegate',
      examples: {'actorName' : 'Roman'});
  
  becameVoterStoryMessage(actorName) => Intl.message(
      "$actorName became voter",
      name: 'becameVoterStory',
      args: [actorName],
      desc: 'Story about becaming a voter',
      examples: {'actorName' : 'Roman'});
}
