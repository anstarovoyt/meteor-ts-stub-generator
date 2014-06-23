package kkey.generator.blocks;


import java.util.ArrayList;
import java.util.List;

public class MethodParameterDeclaration extends ArgumentDeclaration {
  private boolean myIsVarArgs;
  private List<MethodParameterDeclaration> myMergedParameters = new ArrayList<>();


  public MethodParameterDeclaration(String name, String type, String rawType, String description, boolean isRequired) {
    super(name, type, rawType, description, isRequired);
    this.myIsVarArgs = false;
  }

  public MethodParameterDeclaration(String name, String type, String rawType, String description) {
    this(name, type, rawType, description, true);
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

  @Override
  public String toStringWithIndent(String indent) {
    if (isVarArgs()) {
      return "..." + determineArgValue() + ":" + getType() + "[]";
    }
    else {
      return super.toStringWithIndent(indent);
    }
  }

  private String determineArgValue() {
    String value = getName().split(",")[0].trim();
    String substring = value.substring(0, value.length() - 1);
    return substring + "s";
  }
}
