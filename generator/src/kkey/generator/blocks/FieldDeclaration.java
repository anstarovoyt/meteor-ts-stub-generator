package kkey.generator.blocks;


public class FieldDeclaration extends MemberDeclaration {
  public FieldDeclaration(String name, String type, String descr, String scope, NameSpace nameSpace) {
    super(name, type, descr, scope, nameSpace);

  }

  @Override
  protected String getKeyWords() {
    return myNameSpace.isModule() ? "var " : "";
  }
}
