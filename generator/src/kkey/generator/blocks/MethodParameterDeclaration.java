package kkey.generator.blocks;


public class MethodParameterDeclaration extends  Declaration {
  private final String myDescription;

  public MethodParameterDeclaration(String name, String type, String description) {
    super(name, type);
    myDescription = description;
  }

  public String getDescription() {
    return myDescription;
  }
}
