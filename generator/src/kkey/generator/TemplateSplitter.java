package kkey.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TemplateSplitter {
  private static final Logger logger = Logger.getLogger(TemplateSplitter.class.getName());
  public static final String VAR = "var ";
  private String myRawDocumentation;

  private final LinkedHashMap<String, String> mySplittedStrings = new LinkedHashMap<>();
  private final Collection<String> myFunctions = new ArrayList<>();

  private int index = 0;

  public TemplateSplitter(String rawDocumentation) {
    myRawDocumentation = rawDocumentation;
  }

  public TemplateSplitter splitByTemplates() {
    while (index < myRawDocumentation.length()) {
      if (VAR.equals(myRawDocumentation.substring(index, index + VAR.length()))) {
        parseFunction();
      }
      else {
        parseTemplate();
      }
      skipNotIdentifiers();
    }

    return this;
  }

  public LinkedHashMap<String, String> getResult() {
    return mySplittedStrings;
  }

  public Collection<String> getDefFunctions() {
    return myFunctions;
  }

  private void parseFunction() {
    String function = readUntilEndOfBracedBlock();
    myFunctions.add(function);
  }

  private void parseTemplate() {

    StringBuilder keyBuilder = new StringBuilder();
    while (has()) {
      char currentChar = current();
      index++;
      if (currentChar == '=') {
        break;
      }
      else {
        keyBuilder.append(currentChar);
      }
    }
    String value = readUntilEndOfBracedBlock();

    mySplittedStrings.put(keyBuilder.toString().trim(), value.trim());
  }

  private String readUntilEndOfBracedBlock() {
    StringBuilder valueBuilder = new StringBuilder();
    int openBraces = 0;
    while (has()) {
      char currentChar = current();
      index++;
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
    return valueBuilder.toString();
  }

  @SuppressWarnings("StatementWithEmptyBody")
  private int skipNotIdentifiers() {
    while (has()) {
      if (current() == '/') {
        skipComment();
        continue;
      }

      if (has() && Character.isJavaIdentifierStart(current())) {
        break;
      }

      index++;
    }
    return index;
  }

  private void skipComment() {
    StringBuilder skipped = new StringBuilder();
    if (logger.isLoggable(Level.INFO)) {
      skipped.append(current());
    }
    index++;
    if (has() && current() == '/') {
      while (has()) {
        char current = current();
        if (logger.isLoggable(Level.INFO)) {
          skipped.append(current);
        }
        index++;
        if (current == '\n' || current == '\r') {
          logger.info("SKIPPED:" + skipped);
          break;
        }
      }
    }
  }

  private boolean has() {
    return index < myRawDocumentation.length();
  }

  private char current() {
    return myRawDocumentation.charAt(index);
  }
}
