import 'dart:html';
import 'dart:convert';
import 'package:polymer/polymer.dart';
import 'host.dart';
import 'error.dart';
import 'translation.dart';
import 'translationrecord.dart';

@CustomTag('admin-translation-view')
class AdminTranslationView extends PolymerElement {
  static const String LOCALE = "_locale";
  @observable TranslationInfo translation = null;
  @observable List<TranslationRecord> translationRecords = [];
  @observable TranslationRecord currentRow = null;

  AdminTranslationView.created() : super.created() {
  }
  
  enteredView() {
    startLoadingTranslation();
    super.enteredView();
  }
  
  void startLoadingTranslation() {
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/get_translation_info'
    );
    var request = HttpRequest.getString(uri.toString(), withCredentials : true)
        .then(onTranslationLoaded)
        .catchError((var error) {
          ErrorHandler.handleError(error);
         });
  }
  
  void onTranslationLoaded(String response) {
    Map map = JSON.decode(response);
    if (ErrorHandler.handleResponse(map)) {
      translation = new TranslationInfo.fromMap(map);
      translationRecords = createTranslationRecords(translation);
    }
  }
  
  List<TranslationRecord> createTranslationRecords(TranslationInfo translation) {
    List<TranslationRecord> records = [];
    Map<String, String> processedNames = {};
    List<Map> sourceList = JSON.decode(translation.intlMessagesSource);
    Map ukMap = JSON.decode(translation.intlMessagesUk);
    Map ruMap = JSON.decode(translation.intlMessagesRu);
    Map enMap = JSON.decode(translation.intlMessagesEn);
    for (Map source in sourceList) {
      String name = source["name"];
      if (!processedNames.containsKey(name)) {
        processedNames[name] = name;
        String message = source["message"];
        String description = source["desc"];
        String uk = ukMap[name];
        String ru = ruMap[name];
        String en = enMap[name];
        records.add(new TranslationRecord(name, message, description, uk, ru, en));
      }
    }
    List<String> deadMessages = [];
    List<String> names = [];
    names.addAll(ukMap.keys);
    names.addAll(ruMap.keys);
    names.addAll(enMap.keys);
    for (String name in names) {
      if (name == LOCALE) {
        continue;
      }
      if (!processedNames.containsKey(name)) {
        processedNames[name] = name;
        String message = "Dead message";
        String description = "Dead message";
        String uk = ukMap[name];
        String ru = ruMap[name];
        String en = enMap[name];
        records.add(new TranslationRecord(name, message, description, uk, ru, en));        
      }
    }
    return records;
  }
  
  void updateTranslationInfo(TranslationInfo translation, List<TranslationRecord> translationRecords) {
    Map<String, String> ukMap = {LOCALE : "uk"};
    Map<String, String> ruMap = {LOCALE : "ru"};
    Map<String, String> enMap = {LOCALE : "en"};
    for (TranslationRecord record in translationRecords) {
      ukMap[record.name] = record.uk;
      ruMap[record.name] = record.ru;
      enMap[record.name] = record.en; 
    }
    translation.intlMessagesUk = JSON.encode(ukMap);
    translation.intlMessagesRu = JSON.encode(ruMap);
    translation.intlMessagesEn = JSON.encode(enMap);
  }
  
  void changeRecord(event, detail, target) {
    String currentIndex = target.attributes['data-record'];
    currentRow = translationRecords[int.parse(currentIndex)];
  }
  
  void closePopup(event, detail, target) {
    currentRow = null;
  } 
 
  void saveTranslationInfo(event, detail, target) {
    updateTranslationInfo(translation, translationRecords);
    startSaveTranslationInfo(translation, false);
  }
  
  void saveRowTranslation(event, detail, target) {
    startSaveTranslationInfo(translation, true);
  }

  // TODO: copy paste, abstract this out
  void startSaveTranslationInfo(TranslationInfo translation, bool includeSource) {
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/set_translation_info'
    );
    
    Map<String, String> requestData = translation.toMap(includeSource);
    HttpRequest request = new HttpRequest();
    
    // add an event handler that is called when the request finishes
    request.onReadyStateChange.listen((_) {
      if (request.readyState == HttpRequest.DONE) {
        if (request.status == 200 || request.status == 0) {
          onSetTranslationDone(request.responseText);
        } else {
          ErrorHandler.handleError(request.status);
        }
      }
    });

    request.open("POST", uri.toString(), async: true);
    request.withCredentials = true;
    request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var parts = [];
    requestData.forEach((key, value) {
      parts.add('${Uri.encodeQueryComponent(key)}='
          '${Uri.encodeQueryComponent(value)}');
    });
    var data = parts.join('&');
    request.send(data);  
  }
  
  void onSetTranslationDone(String response) {
    Map map = JSON.decode(response);
    if (ErrorHandler.handleResponse(map)) {
      startLoadingTranslation();
    }
  }
}