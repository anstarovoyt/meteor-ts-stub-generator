package kkey.generator;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kkey.generator.blocks.Declaration;
import kkey.generator.blocks.MethodParameterDeclaration;
import kkey.generator.blocks.OptionDeclaration;

import java.util.ArrayList;
import java.util.Collection;

public class ArgsParsing {
  public static Collection<Declaration> getArgs(JsonElement element) {
    Collection<Declaration> result = new ArrayList<>();
    if (Generator.getElement(element, Generator.MEMBER_NAME).endsWith("()")) {
      return result;
    }
    putArgsWithParams(element, result);
    return result;
  }

  public static void putArgsWithParams(JsonElement element, Collection<Declaration> result) {
    JsonElement args = element.getAsJsonObject().get("args");

    JsonArray array = args.getAsJsonArray();
    for (JsonElement jsonElement : array) {
      JsonObject object = jsonElement.getAsJsonObject();
      JsonElement descr = object.get(Generator.MEMBER_DESCR);
      String descrText = descr == null ? "" : descr.getAsString();
      result.add(new MethodParameterDeclaration(object.get("name").getAsString(),
                                                ParsingUtils.parseType(object.get("type").getAsString()),
                                                descrText));
    }
  }

  public static Declaration getOptions(JsonElement element) {
    JsonElement options = element.getAsJsonObject().get("options");
    if (options == null) {
      return null;
    }

    OptionDeclaration declaration = new OptionDeclaration();
    for (JsonElement jsonElement : options.getAsJsonArray()) {
      declaration.addParameter(new MethodParameterDeclaration(Generator.getElement(jsonElement, "name"),
                                                              ParsingUtils.parseType(Generator.getElement(jsonElement, "type")),
                                                              Generator.getElement(jsonElement, "descr")));
    }

    return declaration;
  }

}
