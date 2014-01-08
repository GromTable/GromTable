import 'package:polymer/polymer.dart';
import 'host.dart';

@CustomTag('facebook-comments-view')
class FacebookCommentsView extends PolymerElement {
  @published String url;
  FacebookCommentsView.created() : super.created() {
  }
  
  String getIframeSrc(String url) {
    Uri uri = new Uri.http(
      Host.serverDomain,
      '/api/facebook_comments',
      {
        'url': url,
      }
    );
    return uri.toString();
  }
}