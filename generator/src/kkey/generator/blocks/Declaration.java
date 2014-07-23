package kkey.generator.blocks;

@SuppressWarnings("UnusedDeclaration")
public class Declaration {
  private final String myName;
  private final String myType;
  protected final NameSpace myNameSpace;
  private final String myDescription;

  public Declaration(String name, String type, String description, NameSpace nameSpace) {
    myName = name;
    myType = type;
    myDescription = description;
    myNameSpace = nameSpace;
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

  public String toString(String indent) {
    return toString();
  }
}
