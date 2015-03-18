package kkey.generator.blocks;


import com.google.common.base.Strings;

import static kkey.generator.blocks.DocUtils.*;

public class MemberDeclaration extends Declaration {
  public static final String LOCUS = "@locus";
  private String myScope;

  public MemberDeclaration(String name, String type, String description, String scope, NameSpace nameSpace) {
    super(name, type, description, nameSpace);
    myScope = scope;
  }

  @Override
  public String toString(String indent) {
    return getDocs(indent) + indent + INDENT + getKeyWords() + fullDeclaration(indent)  + (getType() == null ? "" : ( ":" + getType())) + ";";
  }

  protected String getKeyWords() {
    return "";
  }

  protected String getDocs(String indent) {
    return startDoc(indent) +
           newDocLine(getDescription().replace("\n", DocUtils.newDocLine("", indent) + " "), indent) +
           newDocLine("", indent) +
           (Strings.isNullOrEmpty(myScope) ? "" : newDocLine(LOCUS + " " + myScope, indent)) +
           docPart(indent) +
           closeDoc(indent);
  }

  protected String docPart(String indent) {
    return "";
  }

  protected String fullDeclaration(String indent) {
    return getName();
  }
}
