import 'package:unittest/unittest.dart';
import 'documentsdiff.dart';

String visualizeEditScript(List<DiffPart> editScript) {
  String res = "";
  for (DiffPart part in editScript) {
    switch (part.command) {
      case DiffPart.SAME:
        res += part.text;
        break;
      case DiffPart.ADD:
        res += "+[" + part.text + "]";
        break;
      case DiffPart.REMOVE:
        res += "-[" + part.text + "]";
        break;
      case DiffPart.ADD_FROM:
        res += "+(" + part.text + ")";
        break;
      case DiffPart.REMOVE_FROM:
        res += "-(" + part.text + ")";
        break;
    }
  }
  return res;
}

void main() {
  List<List<Object>> testCases = [
    ["Simple test", "abcdef", "abc1def", 3, "abc+[1]def"],
    ["Remove all", "abc", "", 3, "-[abc]"],
    ["Swap 2 letters", "aabcd", "aabdc", 3, "aab-[cd]+[dc]"],
    ["Copy block", "abc,,,,def", "def,,,,abc", 3, "+(def)-(abc),,,,+(abc)-(def)"],
    ["Copy block2", "abc,,,def", "def,,,abc", 3, "+(def)+(,,,)abc-(,,,)-(def)"],
    ["Copy block3", "abc,def", "def,abc", 3, "+(def)+[,]abc-[,]-(def)"],
    ["Simple sentence",
     "However, your introverted, hardworking-genius-of-a-friend-who-always-gets-stuff-done-without-complaining, may be the first person to tell you how great they actually are. ROIKOI lets their reputation precede them and get them ranked. It’s the anti Klout in some way - the rating does not come from the direct social efforts of someone they is trying to become rated. But rather indirectly and anonymously from their system in the eyes of direct friends and colleagues.",
     "For example, your introverted, hardworking genius of a friend who always gets stuff done without complaining, may be the last person to tell you how great they actually are. Roikoi lets their reputation precede them and get them ranked!!! It’s the anti Klout in some way :- the rating does not come from the direct social efforts of someone who is trying to become rated, but maybe rather indirectly and anonymously from their reputation in the face of direct friends and colleagues.",
     20,
     "-[However]+[For example], your introverted, hardworking-[-genius-of-a-friend-who-always-gets-stuff-done-without-]+[ genius of a friend who always gets stuff done without ]complaining, may be the -[fir]+[la]st person to tell you how great they actually are. R-[OIKOI]+[oikoi] lets their reputation precede them and get them ranked-[.]+[!!!] It’s the anti Klout in some way +[:]- the rating does not come from the direct social efforts of someone -[they]+[who] is trying to become rated-[. But]+[, but maybe] rather indirectly and anonymously from their -[system in the eyes]+[reputation in the face] of direct friends and colleagues."
     ],

    ["Simple sentence2",
     "When a friend or acquaintance asks you for a referral for a job where you work, you have to think long and hard about whether you want to go that extra mile for them.",
     "When a friend or acquaintance asks you or your friends for a referral for a job where you work, you have to think big and cool about whether you want to go that extra mile for them.",
     3,
     "When a friend or acquaintance asks you+[ or your friends] for a referral for a job where you work, you have to think -[lon]+[bi]g and -[hard]+[cool] about whether you want to go that extra mile for them."
     ],
     ["Ukrainian", "Просто так", "Просто так!!!", 3, "Просто так+[!!!]"],
     ["Status ukr",
      """Загальногонаціональний позапартійний проект, створений для того, щоб всі громадяні могли вести діалог з приводу подальших шляхів розвитку громадянського суспільства в нашій країні.

Основні цінності проекту:
- Довіра - прозора система вибору представників;
- Доступність - в діалозі може прийняти участ будь-яка людина;
- Загальнонаціональність - представлені всі верстви населення.""",
      """Загальногонаціональний внепартійний проект, що створений для того, щоб всі громадяни могли проводити діалоги з приводу подальших шляхів розвитку громадського суспільства у нашій країні.

Основні цінності нашого проекту:
- Довіра - це прозора система вибору представників;
- Доступність - в цьому діалозі може прийняти участь будь-яка людина;
- Загальнонаціональність - тут представлені усі верстви населення.""",
      20,
      """Загальногонаціональний -[позапартійний проект,]+[внепартійний проект, що] створений для того, щоб всі громадян-[і могли вести діалог]+[и могли проводити діалоги] з приводу подальших шляхів розвитку громад-[янського суспільства в]+[ського суспільства у] нашій країні.

Основні цінності -[проекту:
- Довіра -]+[нашого проекту:
- Довіра - це] прозора система вибору представників;
- Доступність - в +[цьому ]діалозі може прийняти участ+[ь] будь-яка людина;
- Загальнонаціональність - -[представлені в]+[тут представлені у]сі верстви населення."""
      ]
  ];
  for (List<Object> testCase in testCases) {
    test(testCase[0], () =>
      expect(visualizeEditScript(new DocumentsDiff(testCase[1], testCase[2], testCase[3]).getEditScript()),
        equals(testCase[4]))
    );
  }
}