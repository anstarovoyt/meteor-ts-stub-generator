package kkey.generator.blocks;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
public class NameSpace {

  private final String myName;
  private final Set<Declaration> myDeclarations = new HashSet<>();

  public NameSpace(String name) {
    myName = name;
  }

  public void addDeclaration(Declaration declaration) {
    myDeclarations.add(declaration);
  }
}
