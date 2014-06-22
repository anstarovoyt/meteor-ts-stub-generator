package kkey.generator.blocks;

import java.util.ArrayList;
import java.util.List;

public class OptionDeclaration extends Declaration {

  private List<Declaration> list = new ArrayList<>();
  private boolean isRequired;

  public OptionDeclaration() {
    super("options", "Object");
  }

  public void addParameter(Declaration declaration) {
    list.add(declaration);
  }


  public boolean isRequired() {
    return isRequired;
  }

  public void setRequired(boolean isRequired) {
    this.isRequired = isRequired;
  }
}
