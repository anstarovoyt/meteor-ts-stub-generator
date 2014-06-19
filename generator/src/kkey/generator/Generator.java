package kkey.generator;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.LinkedHashMap;

@SuppressWarnings("UnusedDeclaration")
public class Generator {
  public Generator() {

  }

  public void build(String rawDocumentation) {

  }

  public void parseJSONValue(LinkedHashMap<String, String> map) {
    for (String s : map.values()) {
      JsonElement element = new JsonParser().parse(s);
      
    }

  }

  public LinkedHashMap<String, String> splitByBlocks(String rawDocumentation) {
    LinkedHashMap<String, String> result = new LinkedHashMap<>();
    int index = 0;
    while (index < rawDocumentation.length()) {
      index = parseBlock(result, rawDocumentation, index);
      index = skipNotIdentifiers(rawDocumentation, index);
    }

    return result;
  }

  @SuppressWarnings("StatementWithEmptyBody")
  private int skipNotIdentifiers(String documentation, int index) {
    while (index < documentation.length() && !Character.isJavaIdentifierStart(documentation.charAt(index))) {
      index++;
    }
    return index;
  }

  private int parseBlock(LinkedHashMap<String, String> result, String rawDocumentation, int startBlockIndex) {

    StringBuilder keyBuilder = new StringBuilder();
    StringBuilder valueBuilder = new StringBuilder();
    boolean buildKey = true;
    int counter = startBlockIndex;
    int openBraces = 0;
    while (counter < rawDocumentation.length()) {
      char currentChar = rawDocumentation.charAt(counter);
      counter++;
      if (buildKey) {
        if (currentChar == '=') {
          buildKey = false;
        }
        else {
          keyBuilder.append(currentChar);
        }
        continue;
      }

      valueBuilder.append(currentChar);
      if (currentChar == '{') {
        openBraces++;
      }

      if (currentChar == '}') {
        openBraces--;
        if (openBraces == 0) {
          break;
        }
      }
    }

    result.put(keyBuilder.toString().trim(), valueBuilder.toString().trim());

    return counter;
  }

  public String getTypeScriptStub() {
    return null;
  }
}
