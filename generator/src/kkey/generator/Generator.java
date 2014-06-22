package kkey.generator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kkey.generator.blocks.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("UnusedDeclaration")
public class Generator {
  private static final Logger logger = Logger.getLogger(Generator.class.getName());
  public static final String MEMBER_NAME = "name";
  public static final String MEMBER_DESCR = "descr";
  public static final String MEMBER_SCOPE = "locus";
  public static final JsonParser JSON_PARSER = new JsonParser();

  private final ScriptEngine myEngine;

  public Map<String, NameSpace> getNameSpaces() {
    return myNameSpaces;
  }

  private Map<String, NameSpace> myNameSpaces = new HashMap<>();

  public Generator() {
    ScriptEngineManager engineManager = new ScriptEngineManager();
    myEngine = engineManager.getEngineByName("nashorn");

    try {
      myEngine.eval("var Meteor = {};\n Meteor.release = 'something';");
    }
    catch (ScriptException e) {
      logger.log(Level.SEVERE, "error init", e);
      throw new RuntimeException(e);
    }
  }


  public void build(String rawDocumentation) {
    TemplateSplitter splitter = new TemplateSplitter(rawDocumentation).splitByTemplates();
    parseJSONValue(splitter);
  }

  public void parseJSONValue(TemplateSplitter splitter) {
    initFunctions(splitter);

    for (Map.Entry<String, String> s : splitter.getResult().entrySet()) {
      try {
        JsonElement element = getJsonElement(s);
        String fullName = getElement(element, MEMBER_NAME);

        String rawSpace = ParsingUtils.parseNameSpace(fullName);
        if (rawSpace != null) {
          NameSpace nameSpace = getNameSpace(rawSpace);
          if (ParsingUtils.isField(fullName)) {
            FieldDeclaration fieldDeclaration = new FieldDeclaration(ParsingUtils.parseFieldName(fullName),
                                                                ParsingUtils.getTypeByFullName(fullName),
                                                                getElement(element, MEMBER_DESCR));
            nameSpace.addDeclaration(fieldDeclaration);
            break;
          }

          if (ParsingUtils.isFunction(fullName)) {
            MethodDeclaration methodDeclaration = new MethodDeclaration(ParsingUtils.parseFunctionName(fullName),
                                                                        ParsingUtils.getTypeByFullName(fullName),
                                                                        getElement(element, MEMBER_DESCR),
                                                                        getElement(element, MEMBER_SCOPE));
            nameSpace.addDeclaration(methodDeclaration);
            methodDeclaration.addAllArgs(getArgs(element));

            break;
          }
        }
        else {
          //logger.info(s.getKey());
        }
      }
      catch (Exception e) {
        logger.log(Level.SEVERE, "error. Text\n" + s, e);
      }
    }
  }

  private void initFunctions(TemplateSplitter splitter) {
    for (String function : splitter.getDefFunctions()) {
      try {
        myEngine.eval(function);
      }
      catch (ScriptException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private JsonElement getJsonElement(Map.Entry<String, String> s) throws ScriptException {
    String json = myEngine.eval(" JSON.stringify(" + s.getValue() + ")").toString();
    return JSON_PARSER.parse(json);
  }

  private String getElement(JsonElement element, String member) {
    JsonElement memberElement = element.getAsJsonObject().get(member);
    if (memberElement != null && memberElement.isJsonArray()) {
      StringJoiner joiner = new StringJoiner("\n");
      for (JsonElement jsonElement : memberElement.getAsJsonArray()) {
        joiner.add(jsonElement.getAsString());
      }
      return joiner.toString();
    }

    return memberElement == null ? "" : memberElement.getAsString();
  }

  private Collection<Declaration> getArgs(JsonElement element) {
    Collection<Declaration> result = new ArrayList<>();
    JsonElement args = element.getAsJsonObject().get("args");
    if (args == null && getElement(element, MEMBER_NAME).endsWith("()")) {
      return result;
    }
    JsonArray array = args.getAsJsonArray();
    for (JsonElement jsonElement : array) {
      JsonObject object = jsonElement.getAsJsonObject();
      JsonElement descr = object.get(MEMBER_DESCR);
      String descrText = descr == null ? "" : descr.getAsString();
      result.add(new MethodParameterDeclaration(object.get("name").getAsString(),
                                                ParsingUtils.parseType(object.get("type").getAsString()),
                                                descrText));
    }

    return result;
  }

  private NameSpace getNameSpace(String name) {
    NameSpace nameSpace = myNameSpaces.get(name);
    if (nameSpace == null) {
      nameSpace = new NameSpace(name);
      myNameSpaces.put(name, nameSpace);
    }

    return nameSpace;
  }

  public String getTypeScriptStub() {
    return null;
  }
}
