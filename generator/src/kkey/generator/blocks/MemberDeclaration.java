package kkey.generator.blocks;


import com.google.common.base.Strings;

import static kkey.generator.blocks.DocUtils.*;

public class MemberDeclaration extends Declaration {
  public static final String LOCUS = "@locus";
  private String myScope;

  public MemberDeclaration(String name, String type, String description, String scope) {
    super(name, type, description);
    myScope = scope;
  }

  @Override
  public String toString() {
    return getDocs() + fullDeclaration() + ":" + getType() + ";";
  }

  protected String getDocs() {
    return startDoc() +
           newDocLine(getDescription().replace("\n", DocUtils.newDocLine("") + " ")) +
           newDocLine("") +
           (Strings.isNullOrEmpty(myScope) ? "" : newDocLine(LOCUS + " " + myScope)) +
           docPart() +
           closeDoc();
  }

  protected String docPart() {
    return "";
  }

  protected String fullDeclaration() {
    return INDENT + getName();
  }
}
