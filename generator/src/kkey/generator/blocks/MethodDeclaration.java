package kkey.generator.blocks;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"})
public class MethodDeclaration extends Declaration {
  private final String myDescription;
  private final String myScope;

  public List<Declaration> getArgs() {
    return myArgs;
  }

  private final List<Declaration> myArgs = new ArrayList<>();

  public MethodDeclaration(String name, String type, String description, String scope) {
    super(name, type);
    myDescription = description;
    myScope = scope;
  }

  public void addArg(Declaration declaration) {
    myArgs.add(declaration);
  }

  public void addAllArgs(Collection<Declaration> args) {
    myArgs.addAll(args);
  }

  public boolean hasArg(String name, int index) {
    Declaration declaration = myArgs.get(index);
    return declaration != null && declaration.getName().equals(name);
  }

  public Declaration getArg(int index) {
    return myArgs.get(index);
  }
}
