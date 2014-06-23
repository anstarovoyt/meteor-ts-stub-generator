package kkey.generator.blocks;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

import static kkey.generator.blocks.DocUtils.*;

@SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal", "MismatchedQueryAndUpdateOfCollection"})
public class MethodDeclaration extends MemberDeclaration {

  public List<ArgumentDeclaration> getArgs() {
    return myArgs;
  }

  private final List<ArgumentDeclaration> myArgs = new ArrayList<>();
  private boolean hasOptions;

  public MethodDeclaration(String name, String type, String description, String scope) {
    super(name, type, description, scope);
  }

  public void addArg(ArgumentDeclaration declaration) {
    checkOption(declaration);
    myArgs.add(declaration);
  }

  public void addAllArgs(Collection<ArgumentDeclaration> args) {
    for (ArgumentDeclaration arg : args) {
      checkOption(arg);
    }
    myArgs.addAll(args);
  }

  private void checkOption(ArgumentDeclaration arg) {
    if (arg instanceof OptionsDeclaration) hasOptions = true;
  }

  public boolean hasArg(String name, int index) {
    Declaration declaration = myArgs.get(index);
    return declaration != null && declaration.getName().equals(name);
  }

  public Declaration getArg(int index) {
    return myArgs.get(index);
  }

  @Override
  protected String fullDeclaration() {
    StringBuilder result = new StringBuilder();
    result.append(INDENT);
    result.append(getName());
    result.append("(");

    String addIndent = new String(new char[getName().length() + 1]).replace("\0", " ");
    String adjustedIndent = INDENT + addIndent;
    if (!myArgs.isEmpty()) {
      String joinString = ", ";
      if (hasOptions) {
        joinString = ",\n" + adjustedIndent;
      }

      StringJoiner joiner = new StringJoiner(joinString);
      for (ArgumentDeclaration arg : myArgs) {
        joiner.add(arg.toStringWithIndent(adjustedIndent));
      }

      result.append(joiner.toString());
    }

    result.append(")");

    return result.toString();
  }

  @Override
  protected String docPart() {
    if (myArgs.isEmpty()) return super.docPart();

    StringBuilder result = new StringBuilder();
    result.append(newDocLine(""));
    for (ArgumentDeclaration arg : myArgs) {
      result.append(newDocLine(arg.getArgumentJSDoc()));
    }

    return result.toString();
  }
}
