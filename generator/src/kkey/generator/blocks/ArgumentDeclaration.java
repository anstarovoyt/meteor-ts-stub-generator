package kkey.generator.blocks;


import com.google.common.base.Strings;

public class ArgumentDeclaration extends Declaration {
  private boolean myIsRequired;
  private String myRawType;

  public ArgumentDeclaration(String name, String type, String rawType, String description, boolean isRequired) {
    super(name, type, description);
    myIsRequired = isRequired;
    myRawType = rawType;
  }

  public boolean isRequired() {
    return myIsRequired;
  }

  public void setRequired(boolean isRequired) {
    myIsRequired = isRequired;
  }

  public String getArgumentJSDoc() {
    String descriptionPart = Strings.isNullOrEmpty(getDescription()) ? "" : " - " + getDescription();
    String rawTypePart = Strings.isNullOrEmpty(getRawType()) ? "" : "{" + getRawType() + "} ";
    return "@param " + rawTypePart + coverWithRequired(getName()) + descriptionPart;
  }

  public String toStringWithIndent(String indent) {
    return toString();
  }

  @Override
  public String toString() {
    String requirePart = getRequiredPart();
    return getName() + requirePart + ":" + getType();
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
}
