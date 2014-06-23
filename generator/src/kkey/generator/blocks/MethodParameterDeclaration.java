package kkey.generator.blocks;


import java.util.ArrayList;
import java.util.List;

public class MethodParameterDeclaration extends Declaration {
  private final String myDescription;
  private boolean myIsRequired;
  private boolean myIsVarArgs;
  private List<MethodParameterDeclaration> myMergedParameters = new ArrayList<>();


  public MethodParameterDeclaration(String name, String type, String description, boolean isRequired) {
    super(name, type);
    this.myDescription = description;
    this.myIsRequired = isRequired;
    this.myIsVarArgs = false;
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

  public boolean isVarArgs() {
    return myIsVarArgs;
  }

  public void setVarArgs(boolean value) {
    myIsVarArgs = value;
  }

  public void addMergedParameter(MethodParameterDeclaration mergedParameter) {
    myMergedParameters.add(mergedParameter);
  }
}
