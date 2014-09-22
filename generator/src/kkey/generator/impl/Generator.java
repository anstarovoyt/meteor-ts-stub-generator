package kkey.generator.impl;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Generator {
  private static final Logger logger = Logger.getLogger(Generator.class.getName());
  public static final JsonParser JSON_PARSER = new JsonParser();



  private final ScriptEngine myEngine;

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

  public void process(String rawValueWithJson, boolean isJSON) {
    assert isJSON;
    JsonElement parse = JSON_PARSER.parse(rawValueWithJson);

  }
}
