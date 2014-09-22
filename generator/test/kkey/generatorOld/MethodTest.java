package kkey.generatorOld;


import kkey.generator.blocks.*;
import kkey.generator.oldImpl.ArgsParsing;
import kkey.generator.oldImpl.Generator;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
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
    Assert.assertEquals("isClient", next.getName());
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
    Assert.assertEquals("newBinary", methodDeclaration.getName());
    Assert.assertTrue(methodDeclaration.hasArg("size", 0));
  }

  @Test
  public void testSimpleWithNotRequiredParameter() {
    Generator generator = new Generator();

    generator.build("Template.api.ejsonNewBinary = {\n" +
                    "  id: \"ejson_new_binary\",\n" +
                    "  name: \"EJSON.newBinary([size])\",\n" +
                    "  locus: \"Anywhere\",\n" +
                    "  args: [ {name: \"size\", type: \"Number\", descr: \"The number of bytes of binary data to allocate.\"} ],\n" +
                    "  descr: [\"Allocate a new buffer of binary data that EJSON can serialize.\"]\n" +
                    "}");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("EJSON");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof MethodDeclaration;
    MethodDeclaration methodDeclaration = (MethodDeclaration)next;
    Assert.assertEquals("newBinary", methodDeclaration.getName());
    MethodParameterDeclaration size = (MethodParameterDeclaration)methodDeclaration.getArg(0);
    Assert.assertFalse(size.isRequired());
  }


  @Test
  public void testSimpleWith2NotRequiredParameter() {
    Generator generator = new Generator();

    generator.build("Template.api.ejsonNewBinary = {\n" +
                    "  id: \"ejson_new_binary\",\n" +
                    "  name: \"EJSON.newBinary(size, [notRequired])\",\n" +
                    "  locus: \"Anywhere\",\n" +
                    "  descr: [\"Allocate a new buffer of binary data that EJSON can serialize.\"]\n" +
                    "}");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("EJSON");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof MethodDeclaration;
    MethodDeclaration methodDeclaration = (MethodDeclaration)next;
    Assert.assertEquals("newBinary", methodDeclaration.getName());
    MethodParameterDeclaration size = (MethodParameterDeclaration)methodDeclaration.getArg(0);
    MethodParameterDeclaration notRequired = (MethodParameterDeclaration)methodDeclaration.getArg(1);
    Assert.assertFalse(notRequired.isRequired());
    Assert.assertTrue(size.isRequired());
  }


  @Test
  public void testSimpleWithNotDocumentedParameter() {
    Generator generator = new Generator();

    generator.build("Template.api.ejsonNewBinary = {\n" +
                    "  id: \"ejson_new_binary\",\n" +
                    "  name: \"EJSON.newBinary(size)\",\n" +
                    "  locus: \"Anywhere\",\n" +
                    "  descr: [\"Allocate a new buffer of binary data that EJSON can serialize.\"]\n" +
                    "}");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("EJSON");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof MethodDeclaration;
    MethodDeclaration methodDeclaration = (MethodDeclaration)next;
    Assert.assertEquals("newBinary", methodDeclaration.getName());
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
    Assert.assertEquals("addType", methodDeclaration.getName());
    Assert.assertTrue(methodDeclaration.hasArg("name", 0));
    Assert.assertTrue(methodDeclaration.hasArg("factory", 1));
  }

  @Test
  public void testSimpleWith2OptionalParameter() {
    Generator generator = new Generator();

    generator.build("Template.api.meteor_apply = {\n" +
                    "  id: \"meteor_apply\",\n" +
                    "  name: \"Meteor.apply(name, params [, options] [, asyncCallback])\",\n" +
                    "  locus: \"Anywhere\",\n" +
                    "  descr: [\"Invoke a method passing an array of arguments.\"],\n" +
                    "  args: [\n" +
                    "    {name: \"name\",\n" +
                    "     type: \"String\",\n" +
                    "     descr: \"Name of method to invoke\"},\n" +
                    "    {name: \"params\",\n" +
                    "     type: \"Array\",\n" +
                    "     descr: \"Method arguments\"},\n" +
                    "    {name: \"asyncCallback\",\n" +
                    "     type: \"Function\",\n" +
                    "     descr: \"Optional callback; same semantics as in [`Meteor.call`](#meteor_call).\"}\n" +
                    "  ],\n" +
                    "  options: [\n" +
                    "    {name: \"wait\",\n" +
                    "     type: \"Boolean\",\n" +
                    "     descr: \"(Client only) If true, don't send this method until all previous method calls have completed, and don't send any subsequent method calls until this one is completed.\"},\n" +
                    "    {name: \"onResultReceived\",\n" +
                    "     type: \"Function\",\n" +
                    "     descr: \"(Client only) This callback is invoked with the error or result of the method (just like `asyncCallback`) as soon as the error or result is available. The local cache may not yet reflect the writes performed by the method.\"}\n" +
                    "  ]\n" +
                    "};");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("Meteor");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof MethodDeclaration;
    MethodDeclaration methodDeclaration = (MethodDeclaration)next;
    Assert.assertEquals("apply", methodDeclaration.getName());
    MethodParameterDeclaration name = (MethodParameterDeclaration)methodDeclaration.getArg(0);
    MethodParameterDeclaration params = (MethodParameterDeclaration)methodDeclaration.getArg(1);
    OptionsDeclaration options = (OptionsDeclaration)methodDeclaration.getArg(2);
    MethodParameterDeclaration asyncCallback = (MethodParameterDeclaration)methodDeclaration.getArg(3);
    Assert.assertEquals("name", name.getName());
    Assert.assertEquals("params", params.getName());
    Assert.assertEquals("options", options.getName());
    Assert.assertEquals("asyncCallback", asyncCallback.getName());

    Assert.assertFalse(options.isRequired());
    Assert.assertFalse(asyncCallback.isRequired());
    Assert.assertTrue(name.isRequired());
    Assert.assertTrue(params.isRequired());

  }

  @Test
  public void testVarArgsParameter() {
    Generator generator = new Generator();

    generator.build("Template.api.subscribe = {\n" +
                    "  id: \"meteor_subscribe\",\n" +
                    "  name: \"Meteor.subscribe(name [, arg1, arg2, ... ] [, callbacks])\",\n" +
                    "  locus: \"Client\",\n" +
                    "  descr: [\"Subscribe to a record set.  Returns a handle that provides `stop()` and `ready()` methods.\"],\n" +
                    "  args: [\n" +
                    "    {name: \"name\",\n" +
                    "     type: \"String\",\n" +
                    "     descr: \"Name of the subscription.  Matches the name of the server's `publish()` call.\"},\n" +
                    "    {name: \"arg1, arg2, ...\",\n" +
                    "     type: \"Any\",\n" +
                    "     descr: \"Optional arguments passed to publisher function on server.\"},\n" +
                    "    {name: \"callbacks\",\n" +
                    "     type: \"Function or Object\",\n" +
                    "     descr: \"Optional. May include `onError` and `onReady` callbacks. If a function is passed instead of an object, it is interpreted as an `onReady` callback.\"}\n" +
                    "  ]\n" +
                    "};");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("Meteor");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof MethodDeclaration;
    MethodDeclaration methodDeclaration = (MethodDeclaration)next;
    Assert.assertEquals("subscribe", methodDeclaration.getName());
    MethodParameterDeclaration name = (MethodParameterDeclaration)methodDeclaration.getArg(0);
    MethodParameterDeclaration params = (MethodParameterDeclaration)methodDeclaration.getArg(1);
    Assert.assertEquals("name", name.getName());
    Assert.assertEquals("arg1, arg2, ...", params.getName());

    Assert.assertTrue(name.isRequired());
    Assert.assertTrue(params.isVarArgs());

    Assert.assertEquals(2, methodDeclaration.getArgs().size());
  }

  @Test
  public void testVarArgsNoFormalParameter() {
    Generator generator = new Generator();

    generator.build("Template.api.meteor_call = {\n" +
                    "  id: \"meteor_call\",\n" +
                    "  name: \"Meteor.call(name, param1, param2, ... [, asyncCallback])\",\n" +
                    "  locus: \"Anywhere\",\n" +
                    "  descr: [\"Invokes a method passing any number of arguments.\"],\n" +
                    "  args: [\n" +
                    "    {name: \"name\",\n" +
                    "     type: \"String\",\n" +
                    "     descr: \"Name of method to invoke\"},\n" +
                    "    {name: \"param1, param2, ...\",\n" +
                    "     type: \"EJSON\",\n" +
                    "     descr: \"Optional method arguments\"},\n" +
                    "    {name: \"asyncCallback\",\n" +
                    "     type: \"Function\",\n" +
                    "     descr: \"Optional callback, which is called asynchronously with the error or result after the method is complete. If not provided, the method runs synchronously if possible (see below).\"}\n" +
                    "  ]\n" +
                    "};");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("Meteor");
    Declaration next = space.getDeclarations().iterator().next();
    assert next instanceof MethodDeclaration;
    MethodDeclaration methodDeclaration = (MethodDeclaration)next;
    Assert.assertEquals("call", methodDeclaration.getName());
    MethodParameterDeclaration name = (MethodParameterDeclaration)methodDeclaration.getArg(0);
    MethodParameterDeclaration params = (MethodParameterDeclaration)methodDeclaration.getArg(1);
    Assert.assertEquals("name", name.getName());
    Assert.assertEquals("param1, param2, ...", params.getName());

    Assert.assertTrue(name.isRequired());
    Assert.assertTrue(params.isVarArgs());
    Assert.assertEquals(2, methodDeclaration.getArgs().size());
  }


  @Test
  public void testGetRealName() {
    Assert.assertEquals("name", ArgsParsing.getRealName("[name]"));
    Assert.assertEquals("name", ArgsParsing.getRealName("name"));
  }

  @Test
  public void testIsRequired() {
    Assert.assertTrue(ArgsParsing.isRequired("name"));
    Assert.assertFalse(ArgsParsing.isRequired("[name]"));
  }

  @Test
  public void testArgsForName() {
    List<String> strings = ArgsParsing.argsFromName("Meteor.call(name, param1, param2, ... [, asyncCallback])");

    Assert.assertEquals("name", strings.get(0));
    Assert.assertEquals("param1, param2, ...", strings.get(1));
    Assert.assertEquals("[asyncCallback]", strings.get(2));
  }

  @Test
  public void testArgsForName2() {
    List<String> strings = ArgsParsing.argsFromName("Meteor.subscribe(name [, arg1, arg2, ... ] [, callbacks])");

    Assert.assertEquals("name", strings.get(0));
    Assert.assertEquals("[arg1, arg2, ...]", strings.get(1));
    Assert.assertEquals("[callbacks]", strings.get(2));
  }

  @Test
  //indirect mapping Template.<em>myTemplate</em> -> template
  public void testArgsForNameWithCustomName() {
    List<String> strings = ArgsParsing.argsFromName("UI.renderWithData(Template.<em>myTemplate</em>, data)");

    Assert.assertEquals("template", strings.get(0));
    Assert.assertEquals("data", strings.get(1));
  }

  @Test
  public void testSimpleWithCompositeName() {
    Generator generator = new Generator();

    generator.build("Template.api.accounts_onLogin = {\n" +
                    "  id: \"accounts_onlogin\",\n" +
                    "  name: \"Accounts.onLogin(func) and Accounts.onLoginFailure(func)\",\n" +
                    "  locus: \"Server\",\n" +
                    "  descr: [\"Register a callback to be called after a login attempt.\"],\n" +
                    "  args: [\n" +
                    "    {\n" +
                    "      name: \"func\",\n" +
                    "      type: \"Function\",\n" +
                    "      descr: \"The callback to be called after the login attempt\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "};");

    Map<String, NameSpace> spaces = generator.getNameSpaces();

    NameSpace space = spaces.get("Accounts");
    Iterator<Declaration> iterator = space.getDeclarations().iterator();
    MethodDeclaration methodDeclaration = (MethodDeclaration)iterator.next();
    Assert.assertEquals("onLogin", methodDeclaration.getName());
    methodDeclaration = (MethodDeclaration)iterator.next();
    Assert.assertEquals("onLoginFailure", methodDeclaration.getName());
  }

}
