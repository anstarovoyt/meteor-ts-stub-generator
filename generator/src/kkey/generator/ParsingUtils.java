package kkey.generator;


import java.util.HashMap;

public class ParsingUtils {

  public static final HashMap<String, String> types = new HashMap<>();

  static {
    types.put("Function", "Function");
    types.put("String", "String");
    types.put("Number", "Number");
    types.put("Number", "Number");
    types.put("Boolean", "Boolean");
    types.put("Array of Strings", "String[]");
    types.put("Void", "Void");
  }

  public static String parseType(String type) {
    String parsedType = types.get(type);
    return parsedType == null ? "Object" : parsedType;
  }

  public static boolean isFunction(String name) {
    return -1 != name.indexOf('(') && -1 != name.indexOf(')');
  }

  public static String getNameSpace(String name) {
    return Character.isUpperCase(name.charAt(0)) && name.indexOf('.') > 0 ? name.substring(0, name.indexOf('.') - 1) : null;
  }
}
