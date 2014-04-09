library documentitem;

import 'package:polymer/polymer.dart';

class DocumentItem extends Observable {  
  @observable String id;
  @observable String name;
  @observable String voteDecision;
  @observable String voteByString;
  @observable String beforeName;
  @observable String selectedName;
  @observable String afterName;
  
  DocumentItem(this.id, this.name, this.voteDecision, this.voteByString, this.beforeName, this.selectedName, this.afterName);
}