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

}
