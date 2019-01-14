
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
public class URLEncodeDecode {
  public static void main(String[] args) {
    String url = "http://www.gtwang.org/����?var1=銝剜��&var2=spa ce";

    String encodedURL = encode(url);
    System.out.println("Encoded URL: " + encodedURL);

   // String decodedURL = decode(encodedURL);
   // System.out.println("Decoded URL: " + decodedURL);

  }
  // ����圾蝣澆�
  public String decode(String url) {
    try {
      String prevURL = "";
      String decodeURL = url;
      while(!prevURL.equals(decodeURL)) {
        prevURL = decodeURL;
        decodeURL = URLDecoder.decode( decodeURL, "UTF-8" );
      }
      return decodeURL;
    } catch (UnsupportedEncodingException e) {
      return "Error: " + e.getMessage();
    }
  }
  // ����楊蝣澆�
  public static String encode(String url) {
    try {
      String encodeURL = URLEncoder.encode( url, "UTF-8" );
      return encodeURL;
    } catch (UnsupportedEncodingException e) {
      return "Error: " + e.getMessage();
    }
  }
}