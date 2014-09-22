package kkey.generator.blocks;

public class Declaration {
  public void setName(String name) {
    myName = name;
  }

  private String myName;
  private final String myType;
  protected final NameSpace myNameSpace;
  private final String myDescription;

  public Declaration(String name, String type, String description, NameSpace nameSpace) {
    myName = name.trim();
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
