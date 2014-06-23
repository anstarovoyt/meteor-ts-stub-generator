package kkey.generator;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kkey.generator.blocks.Declaration;
import kkey.generator.blocks.MethodParameterDeclaration;
import kkey.generator.blocks.OptionsDeclaration;

import java.util.*;

public class ArgsParsing {
  public static Collection<Declaration> getArgs(JsonElement element) {
    Collection<Declaration> result = new ArrayList<>();
    String fullName = Generator.getElement(element, Generator.MEMBER_NAME);
    if (fullName.endsWith("()")) {
      return result;
    }
    List<String> argsFromName = argsFromName(fullName);
    LinkedHashMap<String, Declaration> directArgs = getArgsFromField(element, new HashSet<>(argsFromName));

    if (directArgs.isEmpty()) {
      for (String name : argsFromName) {
        if (isOption(name)) {
          result.add(getOptions(element, isRequired(name)));
        }
        else {
          result.add(new MethodParameterDeclaration(getRealName(name), "Object", "", isRequired(name)));
        }
      }
    }
    else {
      for (String name : argsFromName) {
        if (isOption(name)) {
          result.add(getOptions(element, isRequired(name)));
        }
        else {
          MethodParameterDeclaration e = (MethodParameterDeclaration)directArgs.get(getRealName(name));
          result.add(e);
          if (e.isVarArgs()) {
            //other should be merged
            break;
          }
        }
      }
    }

    return result;
  }

  public static LinkedHashMap<String, Declaration> getArgsFromField(JsonElement element, Collection<String> argsNames) {
    LinkedHashMap<String, Declaration> result = new LinkedHashMap<>();

    JsonElement args = element.getAsJsonObject().get("args");
    if (args == null) {
      return result;
    }

    JsonArray array = args.getAsJsonArray();

    MethodParameterDeclaration prevParameter = null;
    for (JsonElement jsonElement : array) {
      JsonObject object = jsonElement.getAsJsonObject();
      JsonElement descr = object.get(Generator.MEMBER_DESCR);
      String descrText = descr == null ? "" : descr.getAsString();
      String name = object.get("name").getAsString();
      MethodParameterDeclaration methodParameterDeclaration = new MethodParameterDeclaration(name,
                                                                                             ParsingUtils
                                                                                               .parseType(object.get("type").getAsString()),
                                                                                             descrText);
      methodParameterDeclaration.setRequired(!argsNames.contains("[" + name + "]"));
      methodParameterDeclaration.setVarArgs(name.contains("..."));

      if (prevParameter != null && prevParameter.isVarArgs()) {
        //we cannot have varargs and rest parameter
        prevParameter.addMergedParameter(methodParameterDeclaration);
      }
      else {
        result.put(name, methodParameterDeclaration);
        prevParameter = methodParameterDeclaration;
      }
    }

    return result;
  }

  public static boolean isOption(String name) {
    return name.equals("options") || name.equals("[options]");
  }

  public static Declaration getOptions(JsonElement element, boolean isRequired) {
    JsonElement options = element.getAsJsonObject().get("options");

    OptionsDeclaration declaration = new OptionsDeclaration(isRequired);
    if (options == null) {
      return declaration;
    }
    for (JsonElement jsonElement : options.getAsJsonArray()) {
      declaration.addParameter(new MethodParameterDeclaration(Generator.getElement(jsonElement, "name"),
                                                              ParsingUtils.parseType(Generator.getElement(jsonElement, "type")),
                                                              Generator.getElement(jsonElement, "descr")));
    }

    return declaration;
  }

  public static List<String> argsFromName(String fullName) {
    fullName = fullName.replace("[,", ",[");
    fullName = fullName.replace("Template.<em>myTemplate</em>", "template");
    List<String> result = new ArrayList<>();

    boolean hasVarArgs = fullName.contains("...");
    boolean startVarArgs = false;
    StringJoiner varArgs = new StringJoiner(", ");
    for (String s : fullName.substring(fullName.indexOf('(') + 1, fullName.lastIndexOf(')')).split(",")) {
      s = s.trim();
      if (s.startsWith("[")) {
        s = "[" + s.substring(1, s.length()).trim();
      }

      if (s.endsWith("]")) {
        s = s.substring(0, s.length() - 1).trim() + "]";
      }

      if (hasVarArgs && (Character.isDigit(s.charAt(s.length() - 1)) || s.contains("..."))) {
        if (!startVarArgs) startVarArgs = true;
        varArgs.add(s);

        if (s.contains("...")) {
          startVarArgs = false;
          result.add(varArgs.toString());
        }
      }
      else {
        result.add(s);
      }
    }
    if (startVarArgs) {
      throw new RuntimeException("incorrect state");
    }

    return result;
  }

  public static boolean isRequired(String paramName) {
    return !paramName.trim().startsWith("[") || !paramName.trim().endsWith("]");
  }

  public static String getRealName(String paramName) {
    String trimmedValue = paramName.trim();
    return isRequired(paramName) ? trimmedValue : trimmedValue.substring(1, trimmedValue.length() - 1);
  }
}
