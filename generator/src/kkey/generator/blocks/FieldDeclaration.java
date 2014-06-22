package kkey.generator.blocks;


public class FieldDeclaration extends Declaration {
  private final String myDescr;

  public FieldDeclaration(String name, String type, String descr) {
    super(name, type);
    myDescr = descr;
  }

  public String getDescr() {
    return myDescr;
  }
}
