package kkey.generator.blocks;

import java.util.ArrayList;
import java.util.List;

public class OptionsDeclaration extends Declaration {

  private List<Declaration> list = new ArrayList<>();
  private boolean myIsRequired;

  public OptionsDeclaration(boolean isRequired) {
    super("options", "any");
    myIsRequired = isRequired;
  }

  public void addParameter(Declaration declaration) {
    list.add(declaration);
  }


  public boolean isRequired() {
    return myIsRequired;
  }

  public void setRequired(boolean isRequired) {
    this.myIsRequired = isRequired;
  }
}
