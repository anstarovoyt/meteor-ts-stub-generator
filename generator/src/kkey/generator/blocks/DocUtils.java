package kkey.generator.blocks;


public class DocUtils {
  public static final String INDENT = "    ";


  public static String startDoc(String indent) {
    return indent + INDENT + "/**";
  }

  public static String closeDoc(String indent) {
    return '\n' + indent + INDENT + " */\n";
  }

  public static String newDocLine(String text, String indent) {
    String actualText = text.isEmpty() ? "" : " " + text;
    return "\n" + indent + INDENT + " *" + actualText;
  }
}
