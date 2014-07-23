package kkey.generator.blocks;


import com.google.common.base.Strings;

import java.util.HashMap;

public class ArgumentDeclaration extends Declaration {

  //keywords replacement
  public static final HashMap<String, String> keywords = new HashMap<>();

  static {
    keywords.put("function", "func");
    keywords.put("new", "newValue");
    keywords.put("return", "result");
    keywords.put("var", "variable");
  }

  private boolean myIsRequired;
  private String myRawType;

  public ArgumentDeclaration(String name, String type, String rawType, String description, boolean isRequired, NameSpace nameSpace) {
    super(name, type, description, nameSpace);
    myIsRequired = isRequired;
    myRawType = rawType;
  }

  public boolean isRequired() {
    return myIsRequired;
  }

  public void setRequired(boolean isRequired) {
    myIsRequired = isRequired;
  }

  public String getArgumentJSDoc(String indent) {
    String descriptionPart = Strings.isNullOrEmpty(getDescription()) ? "" : " - " + getDescription();
    descriptionPart = descriptionPart.replace("\n", DocUtils.newDocLine("", indent) + " ");
    String rawTypePart = Strings.isNullOrEmpty(getRawType()) ? "" : "{" + getRawType() + "} ";
    return "@param " + rawTypePart + coverWithRequired(getFixedName()) + descriptionPart;
  }

  public String toStringWithIndent(String indent) {
    return toString();
  }

  @Override
  public String toString() {
    String requirePart = getRequiredPart();
    return getFixedName() + requirePart + ":" + getType();
  }

  protected String getRequiredPart() {
    return isRequired() ? "" : "?";
  }

  private String coverWithRequired(String name) {
    return isRequired() ? name : "[" + name + "]";
  }

  public String getRawType() {
    return myRawType;
  }

  protected String getFixedName() {
    String name = getName();
    return keywords.containsKey(name) ? keywords.get(name) : name;
  }
}
