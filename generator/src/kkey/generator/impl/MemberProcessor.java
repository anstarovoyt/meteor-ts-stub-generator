package kkey.generator.impl;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kkey.generator.blocks.FieldDeclaration;
import kkey.generator.blocks.MemberDeclaration;
import kkey.generator.blocks.MethodDeclaration;
import kkey.generator.blocks.NameSpace;
import kkey.generator.oldImpl.ParsingUtils;

import java.util.Map;

import static kkey.generator.impl.Generator.*;

public class MemberProcessor {


  private String myName;

  public JsonObject getDef() {
    return myDef;
  }

  private final JsonObject myDef;
  private final NameSpace mySpace;
  private boolean myIsConstructor = false;


  public MemberProcessor(String name, JsonObject def, NameSpace space, boolean isConstructor) {
    this(name, def, space);
    myIsConstructor = isConstructor;
  }

  public MemberProcessor(String name, JsonObject def, NameSpace space) {
    myName = name;
    myDef = def;
    mySpace = space;
  }

  public void processDeclaration() {
    if (getName().contains("#")) {
      //try process SUB NAMESPACE
      processSubNameSpace();
      return;
    }

    if (MEMBER_PROPERTY_VALUE.equals(getKind())) {
      mySpace.addDeclaration(getDeclarationForField());
    }

    if (myIsConstructor || FUNCTION_PROPERTY_VALUE.equals(getKind())) {
      mySpace.addDeclaration(getDeclarationForFunction());
    }
  }

  private void processSubNameSpace() {
    String[] splitedName = getName().split("#");
    String subName = splitedName[0];
    String methodNameForSub = splitedName[1];

    NameSpace sub = getAndInitSubNameSpace(getNameSpace(), subName);

    new MemberProcessor(methodNameForSub, myDef, sub).processDeclaration();
  }


  private MemberDeclaration getDeclarationForFunction() {
    MethodDeclaration declaration = new MethodDeclaration(getName(), getReturnType(), getDescription(), getScope(), getNameSpace());

    new ArgsProcessor(this, declaration).fillArgs();

    return declaration;
  }

  public NameSpace getNameSpace() {
    return mySpace;
  }

  private FieldDeclaration getDeclarationForField() {
    return new FieldDeclaration(getName(), getReturnType(), getDescription(), getScope(), getNameSpace());
  }

  public String getKind() {
    return Generator.getString(myDef, KIND_PROPERTY);
  }

  public String getName() {
    return myName;
  }

  public String getReturnType() {
    String predefinedType = PredefinedTypes.getType(getLongName());
    if (predefinedType != null) {
      return predefinedType;
    }

    if (myIsConstructor) {
      return getName();
    }


    return ParsingUtils.getTypeByFields(getLongName(), getDescription(), getRawType());
  }

  public String getRawType() {
    JsonElement typeElement = myDef.get(TYPE_PROPERTY);
    if (typeElement == null) return null;
    if (typeElement.isJsonPrimitive()) return getString(myDef, TYPE_PROPERTY);

    for (Map.Entry<String, JsonElement> entry : typeElement.getAsJsonObject().entrySet()) {
      if ("names".equals(entry.getKey())) return entry.getValue().getAsString();
    }

    return null;
  }

  public String getLongName() {
    return getString(myDef, LONGNAME_PROPERTY);
  }

  public String getDescription() {
    return getString(myDef, DESC_PROPERTY);
  }

  public String getScope() {
    return getString(myDef, SCOPE_PROPERTY);
  }
}
