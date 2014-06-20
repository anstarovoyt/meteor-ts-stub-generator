package kkey.generator.blocks;


public class MethodParameterDeclaration {
  private final String myName;
  private final String myType;
  private final String myDescription;

  public MethodParameterDeclaration(String name, String type, String description) {
    myName = name;
    myType = type;
    myDescription = description;
  }
}
