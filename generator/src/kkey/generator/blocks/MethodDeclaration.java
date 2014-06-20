package kkey.generator.blocks;


import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"})
public class MethodDeclaration {
  private final String myName;
  private final String myType;
  private final String myDescription;
  private final String myScope;

  private final List<Declaration> args = new ArrayList<>();

  public MethodDeclaration(String name, String type, String description, String scope) {

    myName = name;
    myType = type;
    myDescription = description;
    myScope = scope;
  }

  public void addArg(Declaration declaration) {
    args.add(declaration);
  }
}
