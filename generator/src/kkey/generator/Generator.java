package kkey.generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kkey.generator.blocks.FieldDeclaration;
import kkey.generator.blocks.MethodDeclaration;
import kkey.generator.blocks.NameSpace;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("UnusedDeclaration")
public class Generator {
  public static final String MEMBER_TYPE = "type";
  public static final String MEMBER_NAME = "name";
  public static final String MEMBER_DESCR = "descr";
  public static final String MEMBER_SCOPE = "locus";
  public static final JsonParser JSON_PARSER = new JsonParser();

  private static final Logger logger = Logger.getLogger(Generator.class.getName());


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
        String fullName = getJSObjectMemberText(element, MEMBER_NAME);

        String rawSpace = ParsingUtils.parseNameSpace(fullName);
        if (ParsingUtils.isFunction(fullName)) {
          String methodName = fullName.substring(0, fullName.indexOf("("));
          if (methodName.indexOf('.') != methodName.lastIndexOf('.')) {
            //initialize second namespace

            if (fullName.startsWith("new ") || fullName.startsWith("Template.<em>myTemplate</em>")) {
              logger.warning("Skipped (function for undefined property) " + fullName);
              continue;
            }
            logger.info("Has twice namespace! " + fullName);
          }
        }
        if (rawSpace != null) {
          NameSpace nameSpace = getNameSpace(rawSpace);
          if (ParsingUtils.isField(fullName)) {
            if (fullName.startsWith("Template.<em>myTemplate</em>")) {
              logger.warning("Skipped (field for undefined property) " + fullName);
              continue;
            }

            FieldDeclaration fieldDeclaration = new FieldDeclaration(ParsingUtils.parseFieldName(fullName),
                                                                     ParsingUtils.getTypeByElement(element),
                                                                     getJSObjectMemberText(element, MEMBER_DESCR),
                                                                     getJSObjectMemberText(element, MEMBER_SCOPE));
            nameSpace.addDeclaration(fieldDeclaration);
            continue;
          }


          if (ParsingUtils.isFunction(fullName)) {
            if (fullName.contains(" and ")) {
              for (String s1 : fullName.split(" and ")) {
                element.getAsJsonObject().addProperty(MEMBER_NAME, s1);
                addFunctionToNameSpace(element, s1, nameSpace);
              }
            }
            else {
              addFunctionToNameSpace(element, fullName, nameSpace);
            }
            continue;
          }

          logger.warning("Skipped (unknown type) " + fullName);
        }
        else {
         // logger.info("Skipped for key " + s.getKey());
        }
      }
      catch (Exception e) {
        logger.log(Level.SEVERE, "error. Text\n" + s, e);
      }
    }
  }

  private void addFunctionToNameSpace(JsonElement element, String fullName, NameSpace nameSpace) {
    MethodDeclaration methodDeclaration = new MethodDeclaration(ParsingUtils.parseFunctionName(fullName),
                                                                ParsingUtils.getTypeByElement(element),
                                                                getJSObjectMemberText(element, MEMBER_DESCR),
                                                                getJSObjectMemberText(element, MEMBER_SCOPE));
    nameSpace.addDeclaration(methodDeclaration);
    methodDeclaration.addAllArgs(ArgsParsing.getArgs(element));
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

  public static String getJSObjectMemberText(JsonElement element, String member) {
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
