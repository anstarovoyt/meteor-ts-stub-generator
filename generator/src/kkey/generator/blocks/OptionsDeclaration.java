package kkey.generator.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static kkey.generator.blocks.DocUtils.INDENT;

public class OptionsDeclaration extends ArgumentDeclaration {


  private List<ArgumentDeclaration> list = new ArrayList<>();

  public OptionsDeclaration(boolean isRequired, NameSpace nameSpace) {
    super("options", "any", "Options", "", isRequired, nameSpace);
  }

  public void addParameter(ArgumentDeclaration declaration) {
    list.add(declaration);
  }

  @Override
  public String toStringWithIndent(String indent) {
    StringBuilder result = new StringBuilder();
    result.append(getName()).append(getRequiredPart()).append(":{\n");
    String addIndent = indent + INDENT;
    result.append(addIndent);
    StringJoiner joiner = new StringJoiner(";\n" + addIndent);
    for (ArgumentDeclaration declaration : list) {
      joiner.add(declaration.toStringWithIndent(""));
    }

    result.append(joiner.toString());
    result.append('\n');
    result.append(indent);
    result.append("}");
    return result.toString();
  }
}
