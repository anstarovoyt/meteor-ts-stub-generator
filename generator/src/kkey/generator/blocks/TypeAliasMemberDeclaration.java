package kkey.generator.blocks;

public class TypeAliasMemberDeclaration extends MemberDeclaration {
  private final String myAliasedMember;

  public TypeAliasMemberDeclaration(String name, String aliasedMember, String doc, NameSpace nameSpace) {
    super(name, null, doc, null, nameSpace);
    myAliasedMember = aliasedMember;
  }

  @Override
  protected String getKeyWords() {
    return "type ";
  }

  @Override
  protected String fullDeclaration(String indent) {
    return getName() + " " + myAliasedMember;
  }
}
