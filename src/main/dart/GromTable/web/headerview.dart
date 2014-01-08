import 'package:polymer/polymer.dart';
import 'package:intl/intl.dart';
import 'viewercontext.dart';

@CustomTag('header-view')
class HeaderView extends PolymerElement {
  ViewerContext viewerContext = ViewerContext.instance;
  HeaderView.created() : super.created() {
  } 
  
  headerMessage() => Intl.message(
      "Public table",
      name: 'publicTable',
      args: [],
      desc: 'Public table, header on the site.',
      examples: {});
}