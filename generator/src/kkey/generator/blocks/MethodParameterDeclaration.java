package kkey.generator.blocks;


public class MethodParameterDeclaration extends  Declaration {
  private final String myDescription;
  private boolean myIsRequired;

  public MethodParameterDeclaration(String name, String type, String description, boolean isRequired) {
    super(name, type);
    myDescription = description;
    this.myIsRequired = isRequired;
  }

  public MethodParameterDeclaration(String name, String type, String description) {
    this(name, type, description, true);
  }

  public String getDescription() {
    return myDescription;
  }

  public boolean isRequired() {
    return myIsRequired;
  }

  public void setRequired(boolean isRequired) {
    myIsRequired = isRequired;
  }
}
