package kkey.generator.impl;

import com.google.common.base.Strings;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kkey.generator.blocks.*;
import kkey.generator.oldImpl.ParsingUtils;

import java.util.StringJoiner;


public class ArgsProcessor {

  public static final String PARAMS_PROPERTY = "params";
  public static final String OPTIONS_PROPERTY = "options";
  public static final String NAME_PROPERTY = "name";
  private MemberProcessor myMemberProcessor;
  private MethodDeclaration myDeclaration;

  public ArgsProcessor(MemberProcessor memberProcessor, MethodDeclaration declaration) {
    myMemberProcessor = memberProcessor;
    myDeclaration = declaration;
  }

  public void fillArgs() {
    JsonElement params = myMemberProcessor.getDef().get(PARAMS_PROPERTY);

    if (params.isJsonArray()) {
      processAsArray(params.getAsJsonArray());
      return;
    }

    //if (params.isJsonObject()) {
    //  processArgParam(params);
    //  return;
    //}

    throw new RuntimeException("Unsupported params type");
  }


  private OptionsDeclaration getOptions(JsonObject jsonObject) {
    JsonElement element = myMemberProcessor.getDef().get(OPTIONS_PROPERTY);
    assert element.isJsonArray();

    OptionsDeclaration optionsDeclaration = new OptionsDeclaration(isRequired(jsonObject), myMemberProcessor.getNameSpace());

    element.getAsJsonArray().forEach(el -> {
      JsonObject optionParam = el.getAsJsonObject();
      String rawName = Generator.getString(optionParam, NAME_PROPERTY);
      for (String name : rawName.split(",")) {
        if (Strings.isNullOrEmpty(rawName)) continue;

        optionsDeclaration.addParameter(getSimpleParameter(name.trim(), optionParam, true));
      }
    });

    return optionsDeclaration;
  }


  private MethodParameterDeclaration getSimpleParameter(String name, JsonObject jsonObject, boolean allOptional) {
    String description = Generator.getString(jsonObject, "description");
    String typeForDescription = getDescriptionArgType(jsonObject);
    String typeForSignature = getTypeForSignature(jsonObject);
    boolean isRequired = isRequired(jsonObject) && !allOptional;
    return new MethodParameterDeclaration(name,
                                          typeForSignature,
                                          typeForDescription,
                                          description,
                                          isRequired,
                                          myMemberProcessor.getNameSpace());
  }

  private void processAsArray(JsonArray array) {

    MethodParameterDeclaration merger = null;
    for (JsonElement element : array) {
      assert element.isJsonObject();
      JsonObject jsonObject = element.getAsJsonObject();
      String name = Generator.getString(jsonObject, "name");

      boolean isVarArgs = isVarArgs(jsonObject);

      if ("options".equals(name)) {
        assert merger == null;
        assert !isVarArgs;
        myDeclaration.addArg(getOptions(jsonObject));
        continue;
      }

      MethodParameterDeclaration parameter = getSimpleParameter(name, jsonObject, false);
      parameter.setVarArgs(isVarArgs);
      if (merger == null) {
        myDeclaration.addArg(parameter);
      }
      else {
        merger.addMergedParameter(parameter);
      }

      if (merger == null && isVarArgs) {
        merger = parameter;
      }
    }
  }


  private String getDescriptionArgType(JsonObject argParam) {
    JsonElement type = getRawType(argParam);
    assert type.isJsonObject();
    JsonObject typeAsJsonObject = type.getAsJsonObject();
    JsonElement names = typeAsJsonObject.get("names");
    return getNamesAsString(names);
  }

  private JsonElement getRawType(JsonObject argParam) {
    return argParam.get("type");
  }

  private String getNamesAsString(JsonElement names) {
    StringJoiner result = new StringJoiner(" or ");
    assert names.isJsonArray();
    names.getAsJsonArray().forEach(name -> result.add(name.getAsString()));

    return result.toString();
  }

  private boolean isRequired(JsonObject obj) {
    return !"true".equals(Generator.getString(obj, "optional"));
  }

  private boolean isVarArgs(JsonObject param) {
    String name = Generator.getString(param, NAME_PROPERTY);
    return name != null && name.contains("...");
  }

  private String getTypeForSignature(JsonObject argParam) {
    JsonElement type = getRawType(argParam);
    assert type.isJsonObject();
    JsonObject typeAsJsonObject = type.getAsJsonObject();
    JsonElement names = typeAsJsonObject.get("names");
    assert names.isJsonArray();
    JsonArray namesAsJsonArray = names.getAsJsonArray();

    return namesAsJsonArray.size() == 1 ? ParsingUtils.parseType(namesAsJsonArray.get(0).getAsString()) : "any";
  }
}
