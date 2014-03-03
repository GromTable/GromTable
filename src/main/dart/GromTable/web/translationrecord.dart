library translationrecord;

import 'package:polymer/polymer.dart';

class TranslationRecord extends Observable {
  @observable String name;
  @observable String message;
  @observable String description;
  @observable String uk;
  @observable String ru;
  @observable String en;
  
  TranslationRecord(this.name, this.message, this.description, this.uk, this.ru, this.en) {
  }
}