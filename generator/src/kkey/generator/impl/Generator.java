package kkey.generator.impl;


import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kkey.generator.blocks.NameSpace;
import kkey.generator.oldImpl.ParsingUtils;

import java.util.*;
import java.util.logging.Logger;

public class Generator {
  private static final Logger logger = Logger.getLogger(Generator.class.getName());
  public static final JsonParser JSON_PARSER = new JsonParser();
  public static final String MEMBER_PROPERTY_VALUE = "member";
  public static final String FUNCTION_PROPERTY_VALUE = "function";
  public static final String NAMESPACE = "namespace";
  public static final String KIND_PROPERTY = "kind";
  public static final String DESC_PROPERTY = "summary";
  public static final String SCOPE_PROPERTY = "locus";
  public static final String TYPE_PROPERTY = "type";
  public static final String LONGNAME_PROPERTY = "longname";

  public static final Set<String> IGNORED_PROPERTIES =
    Sets.newHashSet("kind", "summary", "longname", "locus", "memberof", "options", "params", "instancename", "name", "scope");

  public static final Set<String> SIMPLE_MEMBERS = Sets.newHashSet("function", "member");
  public static final String CLASS_PROPERTY_VALUE = "class";

  private Map<String, NameSpace> myNameSpaces = new LinkedHashMap<>();

  //private final ScriptEngine myEngine;

  public Generator() {
    //ScriptEngineManager engineManager = new ScriptEngineManager();
    //myEngine = engineManager.getEngineByName("nashorn");
    //
    //try {
    //  myEngine.eval("var Meteor = {};\n Meteor.release = 'something';");
    //}
    //catch (ScriptException e) {
    //  logger.log(Level.SEVERE, "error init", e);
    //  throw new RuntimeException(e);
    //}
  }

  private NameSpace getNameSpace(String name) {
    NameSpace nameSpace = myNameSpaces.get(name);
    if (nameSpace == null) {
      nameSpace = new NameSpace(name, false);
      myNameSpaces.put(name, nameSpace);
    }

    return nameSpace;
  }

  public String getTypeScriptStub() {
    StringJoiner result = new StringJoiner("\n\n\n");
    for (NameSpace space : myNameSpaces.values()) {
      if (!space.isSub()) {
        result.add(space.toString());
      }
    }

    return result.toString();
  }

  public Map<String, NameSpace> getNameSpaces() {
    return myNameSpaces;
  }

  public void process(String rawValueWithJson, boolean isJSON) {
    assert isJSON;
    JsonElement parse = JSON_PARSER.parse(rawValueWithJson);
    assert parse.isJsonObject();

    for (Map.Entry<String, JsonElement> entry : parse.getAsJsonObject().entrySet()) {
      JsonElement element = entry.getValue();
      JsonObject jsonObject = element.getAsJsonObject();
      String kind = getString(jsonObject, KIND_PROPERTY);
      String name = entry.getKey();
      if (kind == null || PredefinedTypes.isGlobalNS(name)) {
        if (Character.isUpperCase(name.charAt(0)) && ParsingUtils.isExactValidIdentifier(name)) {
          kind = NAMESPACE;
        }
        else {
          logger.warning("Kind is null " + name);
        }
      }

      if ((MEMBER_PROPERTY_VALUE.equals(kind) || FUNCTION_PROPERTY_VALUE.equals(kind))
          && name.contains("#")
          && PredefinedTypes.isPlainMember(name.substring(0, name.indexOf('#')))) {

        processPlainMember(name, jsonObject);

        continue;
      }

      if (!NAMESPACE.equals(kind)) {
        logger.warning("It is not namespace:" + name + " kind " + kind);
        continue;
      }

      NameSpace space = getNameSpace(name);
      processNameSpace(space, jsonObject);
    }
  }

  private void processPlainMember(String name,  JsonObject jsonObject) {
    String[] strings = name.split("#");
    String namespaceName = strings[0];
    String memberName = strings[1];

    NameSpace space = getNameSpace(namespaceName + "Member");
    space.setGenerateVariable(false);
    new MemberProcessor(memberName, jsonObject, space).processDeclaration();
  }

  protected void processNameSpace(NameSpace space, JsonObject namespaceObject) {

    for (Map.Entry<String, JsonElement> entry : namespaceObject.entrySet()) {
      if (IGNORED_PROPERTIES.contains(entry.getKey())) continue;

      JsonElement rawElement = entry.getValue();
      validate(space.getName(), entry, rawElement);

      JsonObject value = (JsonObject)entry.getValue();


      if (isClass(value)) {
        if (PredefinedTypes.isGlobalNS(entry.getKey())) {
          getNameSpace(entry.getKey());
        }
        else {
          NameSpace subSpace = getAndInitSubNameSpace(space, entry.getKey());
          subSpace.setGenerateVariable(false);
        }

        //constructor exists(!)
        if (isDefined(value) || PredefinedTypes.hasImplConstructor(getFullName(value))) {
          new MemberProcessor(entry.getKey(), value, space, true).processDeclaration();
        }
      }
      else if (isFieldOrFunction(value) && !PredefinedTypes.isSkippedFunction(getFullName(value))) {
        if (ParsingUtils.isValidIdentifier(entry.getKey())) {
          new MemberProcessor(entry.getKey(), value, space).processDeclaration();
        }
        else {
          logger.warning("Not valid identifier '" + entry.getKey() + "' namespace: " + space.getName());
        }
      }
      else if (isInnerNamespace(value)) {
        NameSpace sub = getAndInitSubNameSpace(space, entry.getKey());
        sub.setGenerateVariable(true);
        processNameSpace(sub, value);
      }
      else {
        logger
          .warning("Not member '" + getString(value, KIND_PROPERTY) + "' namespace: " + space.getName() + " property:" + entry.getKey());
      }
    }
  }

  private boolean isInnerNamespace(JsonObject obj) {
    String kind = Generator.getString(obj, KIND_PROPERTY);
    return kind != null && NAMESPACE.equals(kind);
  }

  private void validate(String name, Map.Entry<String, JsonElement> entry, JsonElement rawElement) {
    if (!rawElement.isJsonObject()) {
      throw new RuntimeException("Not object '" + entry.getKey() + "' namespace: " + name);
    }
  }


  public static String getString(JsonObject obj, String name) {
    JsonElement kindObject = obj.get(name);
    return kindObject == null ? null : kindObject.getAsString();
  }

  private boolean isFieldOrFunction(JsonObject obj) {
    String kind = Generator.getString(obj, KIND_PROPERTY);
    return kind != null && SIMPLE_MEMBERS.contains(kind.toLowerCase());
  }

  private boolean isClass(JsonObject obj) {
    String kind = Generator.getString(obj, KIND_PROPERTY);
    return CLASS_PROPERTY_VALUE.equals(kind);
  }

  private String getFullName(JsonObject obj) {
    return Generator.getString(obj, LONGNAME_PROPERTY);
  }

  private boolean isDefined(JsonObject obj) {
    return null != Generator.getString(obj, SCOPE_PROPERTY);
  }

  public static NameSpace getAndInitSubNameSpace(NameSpace parent, String subName) {

    NameSpace sub = parent.getSub(subName);
    if (sub == null) {
      sub = new NameSpace(subName);
      parent.addSubNameSpace(sub);
      parent.setModule(true);
      sub.setGenerateVariable(false);
    }
    return sub;
  }
}
