package kkey.generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kkey.generator.blocks.FieldDeclaration;
import kkey.generator.blocks.NameSpace;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("UnusedDeclaration")
public class Generator {
  private static final Logger logger = Logger.getLogger(Generator.class.getName());
  public static final String MEMBER_NAME = "name";
  public static final String MEMBER_DESCR = "descr";
  public static final JsonParser JSON_PARSER = new JsonParser();

  private final ScriptEngine myEngine;

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
          if (!ParsingUtils.isFunction(fullName)) {
            nameSpace.addDeclaration(new FieldDeclaration(ParsingUtils.parseFieldName(fullName),
                                                          ParsingUtils.getTypeByFullName(fullName),
                                                          getElement(element, MEMBER_DESCR)));
            logger.info("Parsed member: " + fullName);
          }
        }
        else {
          logger.fine(s.getKey());
        }
      }
      catch (Exception e) {
        logger.log(Level.SEVERE, "error. Text\n" + s, e);
        break;
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
    return element.getAsJsonObject().get(member).getAsString();
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
