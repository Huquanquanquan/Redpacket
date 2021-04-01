package cn.mokier.redpacket.utils;

public class ConfigUtils {

  public static String filterVar(String str, String... values) {
    if(values != null) {
      if(values.length % 2 == 0) {
        for(int i = 0;i < values.length;) {
          str = str.replace(values[i], values[i + 1]);
          i += 2;
          if(i >= values.length) {
            break;
          }
        }
      }
    }
    return str;
  }
}
