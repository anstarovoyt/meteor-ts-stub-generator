package kkey.generator;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kkey.generator.blocks.Declaration;
import kkey.generator.blocks.MethodParameterDeclaration;
import kkey.generator.blocks.OptionDeclaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArgsParsing {
  public static Collection<Declaration> getArgs(JsonElement element) {
    Collection<Declaration> result = new ArrayList<>();
    String fullName = Generator.getElement(element, Generator.MEMBER_NAME);
    if (fullName.endsWith("()")) {
      return result;
    }
    List<Declaration> directArgs = getArgsFromField(element);
    boolean hasArgs = !directArgs.isEmpty();

    if (hasArgs) {
      result.addAll(directArgs);
      for (String name : argsFromName(fullName)) {
        if (isOption(name)) {
          result.add(getOptions(element, name.contains("[")));
        }
      }
    }
    else {
      for (String name : argsFromName(fullName)) {

      }
    }

    return result;
  }

  public static List<Declaration> getArgsFromField(JsonElement element) {
    List<Declaration> result = new ArrayList<>();

    JsonElement args = element.getAsJsonObject().get("args");
    if (!(args == null)) {
      return result;
    }

    JsonArray array = args.getAsJsonArray();
    for (JsonElement jsonElement : array) {
      JsonObject object = jsonElement.getAsJsonObject();
      JsonElement descr = object.get(Generator.MEMBER_DESCR);
      String descrText = descr == null ? "" : descr.getAsString();
      result.add(new MethodParameterDeclaration(object.get("name").getAsString(),
                                                ParsingUtils.parseType(object.get("type").getAsString()),
                                                descrText));
    }

    return result;
  }

  public static boolean isOption(String name) {
    String trimedName = name.trim();
    return trimedName.equals("options") || trimedName.equals("[options]");
  }

  public static Declaration getOptions(JsonElement element, boolean isRequired) {
    JsonElement options = element.getAsJsonObject().get("options");

    OptionDeclaration declaration = new OptionDeclaration();
    declaration.setRequired(isRequired);
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

  public static String[] argsFromName(String fullName) {
    return fullName.substring(fullName.indexOf('(') + 1, fullName.lastIndexOf(')')).split(",");
  }
}
