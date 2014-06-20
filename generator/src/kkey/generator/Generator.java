package kkey.generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("UnusedDeclaration")
public class Generator {
  private static final Logger logger = Logger.getLogger(Generator.class.getName());
  public static final String MEMBER_NAME = "name";

  private final ScriptEngine myEngine;

  public Generator() {
    ScriptEngineManager engineManager =
      new ScriptEngineManager();
    myEngine =
      engineManager.getEngineByName("nashorn");

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


    for (String function : splitter.getDefFunctions()) {
      try {
        myEngine.eval(function);
      }
      catch (ScriptException e) {
        throw new RuntimeException(e);
      }
    }

    for (Map.Entry<String, String> s : splitter.getResult().entrySet()) {
      try {
        String json = myEngine.eval(" JSON.stringify(" + s.getValue() + ")").toString();
        JsonElement element = new JsonParser().parse(json);
        JsonElement nameElement = element.getAsJsonObject().get(MEMBER_NAME);
        String nameElementAsString = nameElement.getAsString();

        String space = ParsingUtils.getNameSpace(nameElementAsString);
        if (space == null) {
          logger.info(s.getKey());
        }

      }
      catch (Exception e) {
        logger.log(Level.SEVERE, "error. Text\n" + s, e);
        break;
      }
    }
  }

  private void createManager() {


  }

  public String getTypeScriptStub() {
    return null;
  }
}
