package kkey.generator.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
/**
 * In transformation name space is mapped into typescript interface + variable (if required)
 */
public class NameSpace {

  private final String myName;
  private final Collection<Declaration> myDeclarations = new ArrayList<>();

  private final List<NameSpace> subNameSpaces = new ArrayList<>();

  private boolean myGenerateVariable;

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
  }

  public void addDeclaration(Declaration declaration) {
    myDeclarations.add(declaration);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    result.append("\ninterface ");
    result.append(getInterfaceName());
    result.append(" {");

    for (Declaration declaration : myDeclarations) {
      result.append("\n\n");
      result.append(declaration);
    }

    result.append("\n\n}\n");

    if (myGenerateVariable) {
      result.append("\ndeclare var ");
      result.append(myName).append(":").append(getInterfaceName()).append(";");
    }

    return result.toString();
  }

  private String getInterfaceName() {
    return "I" + myName;
  }
}
