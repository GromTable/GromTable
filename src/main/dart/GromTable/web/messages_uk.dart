library messages_uk;
import 'package:intl/intl.dart';
import 'package:intl/message_lookup_by_library.dart';

final messages = new MessageLookup();

class MessageLookup extends MessageLookupByLibrary {

  get localeName => 'uk';

  final messages = {
    "developersOnly" : () => Intl.message("На даний момент сайт ще не готовий і доступний тільки для розробників. Якщо ви хотіли б допомогти в розвитку цього сайту. Будь ласка, натисніть на це посилання, щоб приєднатися Facebook групу для розробників."),             
    "login" : () => Intl.message("Увійдіть до сайту за допомогою:"),
    "publicTable" : () => Intl.message("Громадській стіл"),
    "loading" : () => Intl.message("Зачекайте, сайт завантажуеться."),
    "userInformation" : () => Intl.message("Інформація про користувача"),
    "logout" : () => Intl.message("Покинути сайт"),
    "delegatedByUser" : () => Intl.message("Представником даного користувача є:"),
    "delegatedThisUser" : () => Intl.message("Даного користувача делегували:"),
    "delegate" : () => Intl.message("Назначте свого предстаника"),
    "documentHeader" : () => Intl.message("Документ № 1"),
    "commands" : () => Intl.message("Доступни команди:"),
    "createDocument" : () => Intl.message("Створити документ"),
    "documentsList" : () => Intl.message("Список документов"),
    "becameDelegate" : () => Intl.message("Балатуватися в депутати"),
    "becameVoter" : () => Intl.message("Перестаты бути депутатом"),
    "userSettings" : () => Intl.message("Настройки"),
  };
}