package kkey.generator;


import kkey.generator.blocks.Declaration;
import kkey.generator.blocks.MethodDeclaration;
import kkey.generator.blocks.NameSpace;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class MethodTest {
  @Test
  public void testSimple() {
    Generator generator = new Generator();

    generator.build("Template.api.isClient = {\n" +
                    "  id: \"meteor_isclient\",\n" +
                    "  name: \"Meteor.isClient()\",\n" +
                    "  locus: \"Anywhere\",\n" +
                    "  descr: [\"Boolean variable.  True if running in client environment.\"]\n" +
                    "};");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("Meteor");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof MethodDeclaration;
    Assert.assertTrue(((MethodDeclaration)next).getName().equals("isClient"));
  }

  @Test
  public void testSimpleWithParameter() {
    Generator generator = new Generator();

    generator.build("Template.api.ejsonNewBinary = {\n" +
                    "  id: \"ejson_new_binary\",\n" +
                    "  name: \"EJSON.newBinary(size)\",\n" +
                    "  locus: \"Anywhere\",\n" +
                    "  args: [ {name: \"size\", type: \"Number\", descr: \"The number of bytes of binary data to allocate.\"} ],\n" +
                    "  descr: [\"Allocate a new buffer of binary data that EJSON can serialize.\"]\n" +
                    "}");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("EJSON");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof MethodDeclaration;
    MethodDeclaration methodDeclaration = (MethodDeclaration)next;
    Assert.assertTrue(methodDeclaration.getName().equals("newBinary"));
    Assert.assertTrue(methodDeclaration.hasArg("size", 0));
  }

  @Test
  public void testSimpleWith2Parameter() {
    Generator generator = new Generator();

    generator.build("Template.api.ejsonAddType = {\n" +
                    "  id: \"ejson_add_type\",\n" +
                    "  name: \"EJSON.addType(name, factory)\",\n" +
                    "  locus: \"Anywhere\",\n" +
                    "  args: [\n" +
                    "    {name: \"name\",\n" +
                    "     type: \"String\",\n" +
                    "     descr: \"A tag for your custom type; must be unique among custom data types defined in your project, and must match the result of your type's `typeName` method.\"\n" +
                    "    },\n" +
                    "    {name: \"factory\",\n" +
                    "     type: \"Function\",\n" +
                    "     descr: \"A function that deserializes a JSON-compatible value into an instance of your type.  This should match the serialization performed by your type's `toJSONValue` method.\"\n" +
                    "    }\n" +
                    "  ],\n" +
                    "  descr: [\"Add a custom datatype to EJSON.\"]\n" +
                    "};");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("EJSON");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof MethodDeclaration;
    MethodDeclaration methodDeclaration = (MethodDeclaration)next;
    Assert.assertTrue(methodDeclaration.getName().equals("addType"));
    Assert.assertTrue(methodDeclaration.hasArg("name", 0));
    Assert.assertTrue(methodDeclaration.hasArg("factory", 1));
  }

}
