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


  public MethodDeclaration(String name, String type, String description, String scope, NameSpace nameSpace) {
    super(name, type, description, scope, nameSpace);
  }

  public void addArg(ArgumentDeclaration declaration) {
    checkOption(declaration);

    if (explOptional()) {
      declaration.setRequired(false);
    }

    myArgs.add(declaration);
  }

  public void addAllArgs(Collection<ArgumentDeclaration> args) {
    boolean optional = explOptional();
    for (ArgumentDeclaration arg : args) {
      checkOption(arg);
      if (optional) {
        arg.setRequired(false);
      }
    }
    myArgs.addAll(args);
  }

  private boolean explOptional() {
    if(myArgs.size() > 0) {
      ArgumentDeclaration last = myArgs.get(myArgs.size() - 1);
      if (!last.isRequired()) {
        return true;
      }
    }
    return false;
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
  protected String fullDeclaration(String indent) {
    StringBuilder result = new StringBuilder();
    result.append(getName());
    result.append("(");

    String addIndent = new String(new char[getName().length() + 1]).replace("\0", " ") + indent;
    String adjustedIndent = INDENT + addIndent;
    if (!myArgs.isEmpty()) {
      String joinString = ", ";
      if (hasOptions) {
        joinString = ",\n" + adjustedIndent;
      }

      StringJoiner joiner = new StringJoiner(joinString);
      boolean hasVarargs = false;
      for (ArgumentDeclaration arg : myArgs) {
        if (hasVarargs) continue;

        joiner.add(arg.toStringWithIndent(adjustedIndent));
        if (arg instanceof MethodParameterDeclaration && ((MethodParameterDeclaration)arg).isVarArgs()) {
          hasVarargs = true;
        }
      }

      result.append(joiner.toString());
    }

    result.append(")");

    return result.toString();
  }


  @Override
  protected String getKeyWords() {
    return myNameSpace.isModule() ? "function " : "";
  }

  @Override
  protected String docPart(String indent) {
    if (myArgs.isEmpty()) return super.docPart(indent);

    StringBuilder result = new StringBuilder();
    result.append(newDocLine("", indent));
    for (ArgumentDeclaration arg : myArgs) {
      result.append(newDocLine(arg.getArgumentJSDoc(indent), indent));
    }

    return result.toString();
  }
}
