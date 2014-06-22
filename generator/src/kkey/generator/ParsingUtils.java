package kkey.generator;


import java.util.HashMap;

public class ParsingUtils {

  public static final HashMap<String, String> types = new HashMap<>();
  public static final HashMap<String, String> predefinedTypes = new HashMap<>();

  static {
    types.put("Function", "Function");
    types.put("String", "String");
    types.put("Number", "Number");
    types.put("Number", "Number");
    types.put("Boolean", "Boolean");
    types.put("Array of Strings", "String[]");
    types.put("Void", "Void");

    predefinedTypes.put("Template.api.isClient", "Boolean");
    predefinedTypes.put("Template.api.isServer", "Boolean");
  }



  public static String parseType(String type) {
    String parsedType = types.get(type);
    return parsedType == null ? "Object" : parsedType;
  }

  public static boolean isFunction(String fullName) {
    return -1 != fullName.indexOf('(') && -1 != fullName.indexOf(')');
  }

  public static String parseNameSpace(String fullName) {
    return Character.isUpperCase(fullName.charAt(0)) && fullName.indexOf('.') > 0 ? fullName.substring(0, fullName.indexOf('.') - 1) : null;
  }

  public static String parseFieldName(String fullName) {
    return fullName.substring(fullName.indexOf('.') + 1);
  }

  public static String parseFunctionName(String fullName) {
    return fullName.substring(fullName.indexOf('.') + 1, fullName.indexOf('('));
  }

  public static String getTypeByFullName(String fullName) {
    return "Object";
  }
}
