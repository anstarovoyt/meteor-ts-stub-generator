package kkey.generator.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
/**
 * In transformation name space is mapped into typescript interface + variable
 */
public class NameSpace {

  private final String myName;
  private final Collection<Declaration> myDeclarations = new ArrayList<>();

  public String getName() {
    return myName;
  }

  public Collection<Declaration> getDeclarations() {
    return myDeclarations;
  }

  public NameSpace(String name) {
    myName = name;
  }

  public void addDeclaration(Declaration declaration) {
    myDeclarations.add(declaration);
  }


}
