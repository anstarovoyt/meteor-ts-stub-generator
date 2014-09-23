package kkey.generator.oldImpl;


import com.google.gson.JsonElement;

import java.util.HashMap;

public class ParsingUtils {

  public static final HashMap<String, String> types = new HashMap<>();
  public static final HashMap<String, String> predefinedTypes = new HashMap<>();

  static {
    types.put("Function", "Function");
    types.put("function", "Function");
    types.put("String", "string");
    types.put("string", "string");
    types.put("Number", "Number");
    types.put("Number", "Number");
    types.put("Boolean", "boolean");
    types.put("boolean", "boolean");
    types.put("bool", "boolean");
    types.put("Array of Strings", "String[]");
    types.put("Void", "Void");
    types.put("MeteorCursor", "MeteorCursor");

    predefinedTypes.put("Meteor.isClient", "boolean");
    predefinedTypes.put("Meteor.isServer", "boolean");
    predefinedTypes.put("<em>collection</em>.find(selector, [options])", "MeteorCursor");
  }


  public static String parseType(String type) {
    String parsedType = types.get(type);
    return parsedType == null ? "any" : parsedType;
  }

  public static boolean isFunction(String fullName) {
    return -1 != fullName.indexOf('(') && fullName.endsWith(")");
  }

  public static boolean isField(String fullName) {
    return !isFunction(fullName) || fullName.endsWith("function ( ) { ... }");
  }

  public static String parseNameSpace(String fullName) {
    //has tag -> cannot process from this place
    if (fullName.matches(".*(<.*>).*")) {
      return null;
    }

    return Character.isJavaIdentifierStart(fullName.charAt(0))
           && Character.isUpperCase(fullName.charAt(0))
           && fullName.indexOf('.') > 0
           ? fullName.substring(0, fullName.indexOf('.'))
           : null;
  }

  public static String parseFieldName(String fullName) {
    if (fullName.endsWith("function ( ) { ... }")) {
      return fullName.substring(fullName.indexOf('.') + 1, fullName.indexOf(" ="));
    }
    return fullName.substring(fullName.indexOf('.') + 1);
  }

  public static String parseFunctionName(String fullName) {
    return fullName.substring(fullName.indexOf('.') + 1, fullName.indexOf('('));
  }

  public static String getTypeByElement(JsonElement element) {
    String text = Generator.getJSObjectMemberText(element, Generator.MEMBER_TYPE);
    if (!text.isEmpty()) {
      return parseType(text);
    }

    if (Generator.getJSObjectMemberText(element, Generator.MEMBER_DESCR).startsWith("Boolean")) {
      return parseType("Boolean");
    }

    String fullName = Generator.getJSObjectMemberText(element, Generator.MEMBER_NAME);
    if (predefinedTypes.containsKey(fullName)) {
      return parseType(predefinedTypes.get(fullName));
    }

    return "any";
  }

  public static String getTypeByFields(String longName, String desc, String rawType) {
    if (rawType != null && !rawType.isEmpty()) {
      return parseType(rawType);
    }

    if (desc != null && desc.startsWith("Boolean")) {
      return parseType("Boolean");
    }

    if (predefinedTypes.containsKey(longName)) {
      return parseType(predefinedTypes.get(longName));
    }

    return "any";
  }

  public static boolean isValidIdentifier(String name) {
    if (name.contains("#")) {
      String[] split = name.split("#");
      return isExactValidIdentifier(split[0]) && isExactValidIdentifier(split[1]);
    }

    return isExactValidIdentifier(name);
  }

  public static boolean isExactValidIdentifier(String name) {
    return !name.chars().anyMatch(i -> !Character.isJavaIdentifierStart((char)i));
  }
}
