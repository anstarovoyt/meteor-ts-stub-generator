package kkey.generator.blocks;

@SuppressWarnings("UnusedDeclaration")
public class Declaration {
  private final String myName;
  private final String myType;

  private final String myDescription;

  public Declaration(String name, String type, String description) {
    myName = name;
    myType = type;
    myDescription = description;
  }

  public String getType() {
    return myType;
  }

  public String getName() {
    return myName;
  }

  public String getDescription() {
    return myDescription;
  }
}
