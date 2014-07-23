package kkey.generator.blocks;

import java.util.*;

@SuppressWarnings("UnusedDeclaration")
/**
 * In transformation name space is mapped into typescript interface + variable (if required)
 */
public class NameSpace {

  private final String myName;
  private final Collection<Declaration> myDeclarations = new ArrayList<>();
  private final boolean myIsModule;
  private boolean myGenerateVariable;
  private boolean isSub = false;
  private final Set<NameSpace> subNameSpaces = new LinkedHashSet<>();

  public boolean isSub() {
    return isSub;
  }

  public void setSub(boolean isSub) {
    this.isSub = isSub;
  }


  public boolean isGenerateVariable() {
    return myGenerateVariable;
  }

  public boolean isModule() {
    return myIsModule;
  }

  public void setGenerateVariable(boolean generateVariable) {
    this.myGenerateVariable = generateVariable;
  }


  public String getName() {
    return myName;
  }

  public Collection<Declaration> getDeclarations() {
    return myDeclarations;
  }

  public NameSpace(String name) {
    myName = name;
    myGenerateVariable = true;
    myIsModule = false;
  }

  public NameSpace(String name, boolean isModule) {
    myName = name;
    myGenerateVariable = true;
    myIsModule = isModule;
  }

  public void addDeclaration(Declaration declaration) {
    myDeclarations.add(declaration);
  }

  @Override
  public String toString() {
    return toString("");
  }


  public String toString(String indent) {
    StringBuilder result = new StringBuilder();

    result.append(indent);

    result.append(getDeclare()).append(" ");
    result.append(getInterfaceName());
    result.append(" {");

    boolean isFirst = true;
    for (Declaration declaration : myDeclarations) {
      result.append("\n\n");
      if (!isFirst) {
        result.append('\n');
      }
      result.append(declaration.toString(indent));
      isFirst = false;
    }

    appendChildNameSpace(result);

    result.append("\n\n").append(indent).append("}\n");

    if (myGenerateVariable && !myIsModule) {
      result.append("\n").append(indent).append("declare var ");
      result.append(myName).append(":").append(getInterfaceName()).append(";");
    }


    return result.toString();
  }

  private void appendChildNameSpace(StringBuilder result) {
    if (subNameSpaces.isEmpty()) return;

    for (NameSpace space : subNameSpaces) {
      result.append("\n\n");
      result.append(space.toString(DocUtils.INDENT));
    }
  }

  private String getDeclare() {
    return myIsModule ? "declare module" : "interface";
  }

  private String getInterfaceName() {
    return (isModule() || !isGenerateVariable() ? "" : "I") + myName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NameSpace space = (NameSpace)o;
    return myName.equals(space.myName);
  }

  @Override
  public int hashCode() {
    return myName.hashCode();
  }

  public void addSubNameSpace(NameSpace space) {
    subNameSpaces.add(space);
  }

  public Set<NameSpace> getSubNameSpaces() {
    return subNameSpaces;
  }
}
