package kkey.generator.blocks;


import java.util.ArrayList;
import java.util.List;

public class MethodParameterDeclaration extends ArgumentDeclaration {
  private boolean myIsVarArgs;
  private List<MethodParameterDeclaration> myMergedParameters = new ArrayList<>();


  public MethodParameterDeclaration(String name, String type, String rawType, String description, boolean isRequired, NameSpace nameSpace) {
    super(name, type, rawType, description, isRequired, nameSpace);
    this.myIsVarArgs = false;
  }

  public MethodParameterDeclaration(String name, String type, String rawType, String description, NameSpace nameSpace) {
    this(name, type, rawType, description, true, nameSpace);
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

  @Override
  public String getArgumentJSDoc(String indent) {
    StringBuilder result = new StringBuilder();
    result.append(super.getArgumentJSDoc(indent));
    for (MethodParameterDeclaration parameter : myMergedParameters) {
      result.append(DocUtils.newDocLine(parameter.getArgumentJSDoc(indent), indent));
    }

    return result.toString();
  }
}
