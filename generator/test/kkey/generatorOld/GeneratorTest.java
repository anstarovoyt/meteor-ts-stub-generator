package kkey.generatorOld;


import kkey.generator.oldImpl.Generator;
import kkey.generator.oldImpl.TemplateSplitter;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

public class GeneratorTest {

  @Test
  public void testSimpleSplit() {
    LinkedHashMap<String, String> map = new TemplateSplitter("Template.api.isClient = {\n" +
                                                                   "  id: \"meteor_isclient\",\n" +
                                                                   "  name: \"Meteor.isClient\",\n" +
                                                                   "  locus: \"Anywhere\",\n" +
                                                                   "  descr: [\"Boolean variable.  True if running in client environment.\"]\n" +
                                                                   "};").splitByTemplates().getResult();
    Assert.assertEquals(1, map.size());
    Assert.assertEquals("{\n" +
                        "  id: \"meteor_isclient\",\n" +
                        "  name: \"Meteor.isClient\",\n" +
                        "  locus: \"Anywhere\",\n" +
                        "  descr: [\"Boolean variable.  True if running in client environment.\"]\n" +
                        "}", map.get("Template.api.isClient"));
  }


  @Test
  public void testSplitWithTwoBlocks() {
    LinkedHashMap<String, String> map = new TemplateSplitter("Template.api.isClient = {\n" +
                                                                   "  id: \"meteor_isclient\",\n" +
                                                                   "  name: \"Meteor.isClient\",\n" +
                                                                   "  locus: \"Anywhere\",\n" +
                                                                   "  descr: [\"Boolean variable.  True if running in client environment.\"]\n" +
                                                                   "};\n" +
                                                                   "\n" +
                                                                   "Template.api.isServer = {\n" +
                                                                   "  id: \"meteor_isserver\",\n" +
                                                                   "  name: \"Meteor.isServer\",\n" +
                                                                   "  locus: \"Anywhere\",\n" +
                                                                   "  descr: [\"Boolean variable.  True if running in server environment.\"]\n" +
                                                                   "};").splitByTemplates().getResult();
    Assert.assertEquals(2, map.size());
    Assert.assertEquals("{\n" +
                        "  id: \"meteor_isclient\",\n" +
                        "  name: \"Meteor.isClient\",\n" +
                        "  locus: \"Anywhere\",\n" +
                        "  descr: [\"Boolean variable.  True if running in client environment.\"]\n" +
                        "}", map.get("Template.api.isClient"));

    Assert.assertEquals("{\n" +
                        "  id: \"meteor_isserver\",\n" +
                        "  name: \"Meteor.isServer\",\n" +
                        "  locus: \"Anywhere\",\n" +
                        "  descr: [\"Boolean variable.  True if running in server environment.\"]\n" +
                        "}", map.get("Template.api.isServer"));
  }

  @Test
  public void testSplitComplexBlock() {
    LinkedHashMap<String, String> map =  new TemplateSplitter("Template.api.ejsonStringify = {\n" +
                                                                   "  id: \"ejson_stringify\",\n" +
                                                                   "  name: \"EJSON.stringify(val, [options])\",\n" +
                                                                   "  locus: \"Anywhere\",\n" +
                                                                   "  args: [ {name: \"val\", type: \"EJSON-compatible value\", descr: \"A value to stringify.\"} ],\n" +
                                                                   "  options: [\n" +
                                                                   "    {name: \"indent\",\n" +
                                                                   "     type: \"Boolean, Integer, or String\",\n" +
                                                                   "     descr: \"Indents objects and arrays for easy readability.  When `true`, indents by 2 spaces; when an integer, indents by that number of spaces; and when a string, uses the string as the indentation pattern.\"},\n" +
                                                                   "    {name: \"canonical\",\n" +
                                                                   "     type: \"Boolean\",\n" +
                                                                   "     descr: \"When `true`, stringifies keys in an object in sorted order.\"}\n" +
                                                                   "  ],\n" +
                                                                   "  descr: [\"Serialize a value to a string.\\n\\nFor EJSON values, the serialization \" +\n" +
                                                                   "          \"fully represents the value. For non-EJSON values, serializes the \" +\n" +
                                                                   "          \"same way as `JSON.stringify`.\"]\n" +
                                                                   "},").splitByTemplates().getResult();
    Assert.assertEquals(1, map.size());
    Assert.assertEquals("{\n" +
                        "  id: \"ejson_stringify\",\n" +
                        "  name: \"EJSON.stringify(val, [options])\",\n" +
                        "  locus: \"Anywhere\",\n" +
                        "  args: [ {name: \"val\", type: \"EJSON-compatible value\", descr: \"A value to stringify.\"} ],\n" +
                        "  options: [\n" +
                        "    {name: \"indent\",\n" +
                        "     type: \"Boolean, Integer, or String\",\n" +
                        "     descr: \"Indents objects and arrays for easy readability.  When `true`, indents by 2 spaces; when an integer, indents by that number of spaces; and when a string, uses the string as the indentation pattern.\"},\n" +
                        "    {name: \"canonical\",\n" +
                        "     type: \"Boolean\",\n" +
                        "     descr: \"When `true`, stringifies keys in an object in sorted order.\"}\n" +
                        "  ],\n" +
                        "  descr: [\"Serialize a value to a string.\\n\\nFor EJSON values, the serialization \" +\n" +
                        "          \"fully represents the value. For non-EJSON values, serializes the \" +\n" +
                        "          \"same way as `JSON.stringify`.\"]\n" +
                        "}", map.get("Template.api.ejsonStringify"));
  }

  @Test
  public void testParse() throws IOException {
    String content = new String(Files.readAllBytes(Paths.get("docs.txt")));

    new Generator().build(content);
  }
}
