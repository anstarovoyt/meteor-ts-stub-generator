package kkey.generator.blocks;

@SuppressWarnings("UnusedDeclaration")
public class Declaration {
  private final String myName;
  private final String myType;

  public Declaration(String name, String type) {
    myName = name;
    myType = type;
  }

  public String getType() {
    return myType;
  }

  public String getName() {
    return myName;
  }
}
