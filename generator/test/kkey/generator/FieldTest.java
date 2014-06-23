package kkey.generator;


import kkey.generator.blocks.Declaration;
import kkey.generator.blocks.FieldDeclaration;
import kkey.generator.blocks.NameSpace;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FieldTest {

  @Test
  public void simpleTest() {
    Generator generator = new Generator();

    generator.build("Template.api.isClient = {\n" +
                    "  id: \"meteor_isclient\",\n" +
                    "  name: \"Meteor.isClient\",\n" +
                    "  locus: \"Anywhere\",\n" +
                    "  descr: [\"Boolean variable.  True if running in client environment.\"]\n" +
                    "};");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("Meteor");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof FieldDeclaration;
    Assert.assertEquals("isClient", next.getName());
  }

  @Test
  public void withFunctionTest() {
    Generator generator = new Generator();

    generator.build("Template.api.template_created = {\n" +
                    "  id: \"template_created\",\n" +
                    "  name: \"Template.created = function ( ) { ... }\",\n" +
                    "  locus: \"Client\",\n" +
                    "  descr: [\"Provide a callback when an instance of a template is created.\"]\n" +
                    "};");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("Template");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof FieldDeclaration;
    Assert.assertEquals("created", next.getName());
  }
}
