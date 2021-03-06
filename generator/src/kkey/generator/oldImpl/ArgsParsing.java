package kkey.generator.oldImpl;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kkey.generator.blocks.ArgumentDeclaration;
import kkey.generator.blocks.MethodParameterDeclaration;
import kkey.generator.blocks.NameSpace;
import kkey.generator.blocks.OptionsDeclaration;

import java.util.*;

public class ArgsParsing {

  public static Collection<ArgumentDeclaration> getArgs(JsonElement element, NameSpace nameSpace) {
    Collection<ArgumentDeclaration> result = new ArrayList<>();
    String fullName = Generator.getJSObjectMemberText(element, Generator.MEMBER_NAME);
    if (fullName.endsWith("()")) {
      return result;
    }
    List<String> argsFromName = argsFromName(fullName);
    LinkedHashMap<String, ArgumentDeclaration> directArgs = getArgsFromField(element, new HashSet<>(argsFromName), nameSpace);

    if (directArgs.isEmpty()) {
      for (String name : argsFromName) {
        if (isOption(name)) {
          result.add(getOptions(element, isRequired(name), nameSpace));
        }
        else {
          result.add(new MethodParameterDeclaration(getRealName(name), "any", "", "", isRequired(name), nameSpace));
        }
      }
    }
    else {

      ArrayList list = new ArrayList<>(directArgs.values());
      int index = -1;
      for (String name : argsFromName) {
        index++;
        if (isOption(name)) {
          result.add(getOptions(element, isRequired(name), nameSpace));
        }
        else {
          String realName = getRealName(name);
          MethodParameterDeclaration e = (MethodParameterDeclaration)directArgs.get(realName);
          if (e == null) {
            assert list.size() == argsFromName.size();
            //have to try use index
            e = (MethodParameterDeclaration)list.get(index);
            if (!ParsingUtils.isValidIdentifier(e.getName())) {
              if (ParsingUtils.isValidIdentifier(realName)) {
                e.setName(realName);
              }
            }
          }
          if (!e.isVarArgs() && !ParsingUtils.isValidIdentifier(e.getName())) {
            throw new RuntimeException("Incorrect identifier for parameter" + e.getName());
          }
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

  public static LinkedHashMap<String, ArgumentDeclaration> getArgsFromField(JsonElement element,
                                                                            Collection<String> argsNames,
                                                                            NameSpace nameSpace) {
    LinkedHashMap<String, ArgumentDeclaration> result = new LinkedHashMap<>();

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
      String rawType = Generator.getJSObjectMemberText(jsonElement, "type");
      MethodParameterDeclaration methodParameterDeclaration = new MethodParameterDeclaration(name,
                                                                                             ParsingUtils.parseType(rawType),
                                                                                             rawType,
                                                                                             descrText, nameSpace);
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

  public static ArgumentDeclaration getOptions(JsonElement element, boolean isRequired, NameSpace nameSpace) {
    JsonElement options = element.getAsJsonObject().get("options");

    OptionsDeclaration declaration = new OptionsDeclaration(isRequired, nameSpace);
    if (options == null) {
      return declaration;
    }
    for (JsonElement jsonElement : options.getAsJsonArray()) {
      String rawType = Generator.getJSObjectMemberText(jsonElement, "type");
      String rawName = Generator.getJSObjectMemberText(jsonElement, "name");

      String[] names = {rawName};
      if (rawName.contains(", ")) {
        names = rawName.split(", ");
      }

      for (String name : names) {
        declaration.addParameter(new MethodParameterDeclaration(name,
                                                                ParsingUtils.parseType(rawType),
                                                                rawType,
                                                                Generator.getJSObjectMemberText(jsonElement, "descr"),
                                                                false, nameSpace));
      }
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
