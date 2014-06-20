package kkey.generator;

import java.util.LinkedHashMap;


public class TemplateSplitter {

  private String myRawDocumentation;

  public TemplateSplitter(String rawDocumentation) {
    myRawDocumentation = rawDocumentation;
  }

  public LinkedHashMap<String, String> splitByTemplates() {
    LinkedHashMap<String, String> result = new LinkedHashMap<>();
    int index = 0;
    while (index < myRawDocumentation.length()) {
      index = parseTemplate(result, index);
      index = skipNotIdentifiers(myRawDocumentation, index);
    }

    return result;
  }

  private int parseTemplate(LinkedHashMap<String, String> result, int startBlockIndex) {

    StringBuilder keyBuilder = new StringBuilder();
    StringBuilder valueBuilder = new StringBuilder();
    boolean buildKey = true;
    int counter = startBlockIndex;
    int openBraces = 0;
    while (counter < myRawDocumentation.length()) {
      char currentChar = myRawDocumentation.charAt(counter);
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

  @SuppressWarnings("StatementWithEmptyBody")
  private int skipNotIdentifiers(String documentation, int index) {
    while (index < documentation.length() && !Character.isJavaIdentifierStart(documentation.charAt(index))) {
      index++;
    }
    return index;
  }
}
