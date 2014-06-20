package kkey.generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.LinkedHashMap;

@SuppressWarnings("UnusedDeclaration")
public class Generator {

  public void build(String rawDocumentation) {
    LinkedHashMap<String, String> templatesToJsonString = new TemplateSplitter(rawDocumentation).splitByTemplates();
  }

  public void parseJSONValue(LinkedHashMap<String, String> map) {
    for (String s : map.values()) {
      JsonElement element = new JsonParser().parse(s);
      
    }

  }





  public String getTypeScriptStub() {
    return null;
  }
}
