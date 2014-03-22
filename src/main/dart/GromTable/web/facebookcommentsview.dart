import 'package:polymer/polymer.dart';
import 'host.dart';

@CustomTag('facebook-comments-view')
class FacebookCommentsView extends PolymerElement {
  @published String url;
  FacebookCommentsView.created() : super.created() {
  }
  
  String getIframeSrc(String url) {
    Uri documentUri = Uri.parse(url);
    // Note: facebook ads queries for tracking purpouse.
    // http://gromstol.org/?fb_action_ids=10200884957525918&fb_action_types=og.comments#document4dd3000400000000000000f6c3c01df2
    Uri withoutQuery = new Uri(
        // Note: we want same comments for http and https
        scheme : 'http',
        host : documentUri.host,
        port : documentUri.port,
        path : documentUri.path,
        fragment : documentUri.fragment);
    Uri uri = new Uri.http(
      Host.apiDomain,
      '/api/facebook_comments',
      {
        'url': withoutQuery.toString(),
      }
    );
    return uri.toString();
  }
}