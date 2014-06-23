package kkey.generator.blocks;


public class DocUtils {
  public static final String INDENT = "    ";


  public static String startDoc() {
    return INDENT + "/**";
  }

  public static String closeDoc() {
    return '\n' + INDENT + " */\n";
  }

  public static String newDocLine(String text) {
    String actualText = text.isEmpty() ? "" : " " + text;
    return "\n" + INDENT + " *" + actualText;
  }
}
