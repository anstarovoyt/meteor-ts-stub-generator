package kkey.generator;

import kkey.generator.blocks.NameSpace;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TypeScriptStubTest {
  @Rule
  public TestName name = new TestName();

  @Test
  public void testGenerateStubWithNoArgsMethod() {
    assertResult(getSpaceText("Meteor", "Template.api.status = {\n" +
                                        "  id: \"meteor_status\",\n" +
                                        "  name: \"Meteor.status()\",\n" +
                                        "  locus: \"Client\",\n" +
                                        "  descr: [\"Get the current connection status. A reactive data source.\"]\n" +
                                        "};\n"));
  }

  @Test
  public void testGenerateStubWithFields() {
    assertResult(getSpaceText("Meteor", "Template.api.isClient = {\n" +
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
                                        "};"));
  }

  @Test
  public void testGenerateStubWithSimpleOneArgumentFunction() {
    assertResult(getSpaceText("Meteor", "Template.api.startup = {\n" +
                                        "  id: \"meteor_startup\",\n" +
                                        "  name: \"Meteor.startup(func)\",\n" +
                                        "  locus: \"Anywhere\",\n" +
                                        "  descr: [\"Run code when a client or a server starts.\"],\n" +
                                        "  args: [\n" +
                                        "    {name: \"func\",\n" +
                                        "     type: \"Function\",\n" +
                                        "     descr: \"A function to run on startup.\"}\n" +
                                        "  ]\n" +
                                        "};"));
  }

  @Test
  public void testGenerateStubWithSimpleOneOptionalArgumentFunction() {
    assertResult(getSpaceText("Meteor", "Template.api.startup = {\n" +
                                        "  id: \"meteor_startup\",\n" +
                                        "  name: \"Meteor.startup([func])\",\n" +
                                        "  locus: \"Anywhere\",\n" +
                                        "  descr: [\"Run code when a client or a server starts.\"],\n" +
                                        "  args: [\n" +
                                        "    {name: \"func\",\n" +
                                        "     type: \"Function\",\n" +
                                        "     descr: \"A function to run on startup.\"}\n" +
                                        "  ]\n" +
                                        "};"));
  }

  @Test
  public void testGenerateStubWithSimpleTwoArgumentFunction() {
    assertResult(getSpaceText("Meteor", "Template.api.publish = {\n" +
                                        "  id: \"meteor_publish\",\n" +
                                        "  name: \"Meteor.publish(name, func)\",\n" +
                                        "  locus: \"Server\",\n" +
                                        "  descr: [\"Publish a record set.\"],\n" +
                                        "  args: [\n" +
                                        "    {name: \"name\",\n" +
                                        "     type: \"String\",\n" +
                                        "     descr: \"Name of the record set.  If `null`, the set has no name, and the record set is automatically sent to all connected clients.\"},\n" +
                                        "    {name: \"func\",\n" +
                                        "     type: \"Function\",\n" +
                                        "     descr: \"Function called on the server each time a client subscribes.  Inside the function, `this` is the publish handler object, described below.  If the client passed arguments to `subscribe`, the function is called with the same arguments.\"}\n" +
                                        "  ]\n" +
                                        "};"));
  }

  @Test
  public void testGenerateStubWithOptionFunction() {
    assertResult(getSpaceText("Meteor", "Template.api.allow = {\n" +
                                        "    id: \"allow\",\n" +
                                        "    name: \"Meteor.allow(options)\",\n" +
                                        "    locus: \"Server\",\n" +
                                        "    descr: [\"Allow users to write directly to this collection from client code, subject to limitations you define.\"],\n" +
                                        "    options: [\n" +
                                        "        {name: \"insert\",\n" +
                                        "            type: \"Function\",\n" +
                                        "            descr: \"Functions that look at a proposed modification to the database and return true if it should be allowed.\"},\n" +
                                        "        {name: \"fetch\",\n" +
                                        "            type: \"Array of String\",\n" +
                                        "            descr: \"Optional performance enhancement. Limits the fields that will be fetched from the database for inspection by your `update` and `remove` functions.\"},\n" +
                                        "        {name: \"transform\",\n" +
                                        "            type: \"Function\",\n" +
                                        "            descr: \"Overrides `transform` on the  [`Collection`](#collections).  Pass `null` to disable transformation.\"}\n" +
                                        "    ]\n" +
                                        "};\n"));
  }

  @Test
  public void testGenerateStubWithCustomOptionFunction() {
    assertResult(getSpaceText("Meteor", "Template.api.allow = {\n" +
                                        "  id: \"allow\",\n" +
                                        "  name: \"Meteor.allow(options)\",\n" +
                                        "  locus: \"Server\",\n" +
                                        "  descr: [\"Allow users to write directly to this collection from client code, subject to limitations you define.\"],\n" +
                                        "  options: [\n" +
                                        "    {name: \"insert, update, remove\",\n" +
                                        "     type: \"Function\",\n" +
                                        "     descr: \"Functions that look at a proposed modification to the database and return true if it should be allowed.\"},\n" +
                                        "    {name: \"fetch\",\n" +
                                        "     type: \"Array of String\",\n" +
                                        "     descr: \"Optional performance enhancement. Limits the fields that will be fetched from the database for inspection by your `update` and `remove` functions.\"},\n" +
                                        "    {name: \"transform\",\n" +
                                        "     type: \"Function\",\n" +
                                        "     descr: \"Overrides `transform` on the  [`Collection`](#collections).  Pass `null` to disable transformation.\"}\n" +
                                        "  ]\n" +
                                        "};"));
  }


  @Test
  public void testGenerateStubWithOptionFunctionAccountsReal() {
    assertResult(getSpaceText("Accounts", "Template.api.accounts_config = {\n" +
                                          "  id: \"accounts_config\",\n" +
                                          "  name: \"Accounts.config(options)\",\n" +
                                          "  locus: \"Anywhere\",\n" +
                                          "  descr: [\"Set global accounts options.\"],\n" +
                                          "  options: [\n" +
                                          "    {\n" +
                                          "      name: \"sendVerificationEmail\",\n" +
                                          "      type: \"Boolean\",\n" +
                                          "      descr: \"New users with an email address will receive an address verification email.\"\n" +
                                          "    },\n" +
                                          "    {\n" +
                                          "      name: \"forbidClientAccountCreation\",\n" +
                                          "      type: \"Boolean\",\n" +
                                          "      descr: \"Calls to [`createUser`](#accounts_createuser) from the client will be rejected. In addition, if you are using [accounts-ui](#accountsui), the \\\"Create account\\\" link will not be available.\"\n" +
                                          "    },\n" +
                                          "    {\n" +
                                          "      name: \"restrictCreationByEmailDomain\",\n" +
                                          "      type: \"String or Function\",\n" +
                                          "      descr: \"If set to a string, only allows new users if the domain part of their email address matches the string. If set to a function, only allows new users if the function returns true.  The function is passed the full email address of the proposed new user.  Works with password-based sign-in and external services that expose email addresses (Google, Facebook, GitHub). All existing users still can log in after enabling this option. Example: `Accounts.config({ restrictCreationByEmailDomain: 'school.edu' })`.\"\n" +
                                          "    },\n" +
                                          "    {\n" +
                                          "      name: \"loginExpirationInDays\",\n" +
                                          "      type: \"Number\",\n" +
                                          "      descr: \"The number of days from when a user logs in until their token expires and they are logged out. Defaults to 90. Set to `null` to disable login expiration.\"\n" +
                                          "    },\n" +
                                          "    {\n" +
                                          "      name: \"oauthSecretKey\",\n" +
                                          "      type: \"String\",\n" +
                                          "      descr: \"When using the `oauth-encryption` package, the 16 byte key using to encrypt sensitive account credentials in the database, encoded in base64.  This option may only be specifed on the server.  See packages/oauth-encryption/README.md for details.\"\n" +
                                          "    }\n" +
                                          "  ]\n" +
                                          "};"));
  }

  @Test
  public void testGenerateStubWithParamAndOption() {
    assertResult(getSpaceText("Meteor", "Template.api.absoluteUrl = {\n" +
                                          "  id: \"meteor_absoluteurl\",\n" +
                                          "  name: \"Meteor.absoluteUrl([path], [options])\",\n" +
                                          "  locus: \"Anywhere\",\n" +
                                          "  descr: [\"Generate an absolute URL pointing to the application. The server \"\n" +
                                          "          + \"reads from the `ROOT_URL` environment variable to determine \"\n" +
                                          "          + \"where it is running. This is taken care of automatically for \"\n" +
                                          "          + \"apps deployed with `meteor deploy`, but must be provided when \"\n" +
                                          "          + \"using `meteor bundle`.\"],\n" +
                                          "  args: [\n" +
                                          "    {name: \"path\",\n" +
                                          "     type: \"String\",\n" +
                                          "     descr: 'A path to append to the root URL. Do not include a leading \"`/`\".'\n" +
                                          "    }\n" +
                                          "  ],\n" +
                                          "  options: [\n" +
                                          "    {name: \"secure\",\n" +
                                          "     type: \"Boolean\",\n" +
                                          "     descr: \"Create an HTTPS URL.\"\n" +
                                          "    },\n" +
                                          "    {name: \"replaceLocalhost\",\n" +
                                          "     type: \"Boolean\",\n" +
                                          "     descr: \"Replace localhost with 127.0.0.1. Useful for services that don't recognize localhost as a domain name.\"},\n" +
                                          "    {name: \"rootUrl\",\n" +
                                          "     type: \"String\",\n" +
                                          "     descr: \"Override the default ROOT_URL from the server environment. For example: \\\"`http://foo.example.com`\\\"\"\n" +
                                          "    }\n" +
                                          "  ]\n" +
                                          "};"));
  }

  @Test
  public void testGenerateStubForVarArgs() {
    assertResult(getSpaceText("Meteor", "Template.api.subscribe = {\n" +
                                        "  id: \"meteor_subscribe\",\n" +
                                        "  name: \"Meteor.subscribe(name [, arg1, arg2, ... ])\",\n" +
                                        "  locus: \"Client\",\n" +
                                        "  descr: [\"Subscribe to a record set.  Returns a handle that provides `stop()` and `ready()` methods.\"],\n" +
                                        "  args: [\n" +
                                        "    {name: \"name\",\n" +
                                        "     type: \"String\",\n" +
                                        "     descr: \"Name of the subscription.  Matches the name of the server's `publish()` call.\"},\n" +
                                        "    {name: \"arg1, arg2, ...\",\n" +
                                        "     type: \"Any\",\n" +
                                        "     descr: \"Optional arguments passed to publisher function on server.\"}\n" +
                                        "  ]\n" +
                                        "};"));
  }

  @Test
  public void testGenerateStubForVarArgsAndOptional() {
    assertResult(getSpaceText("Meteor", "Template.api.subscribe = {\n" +
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
                                        "};"));
  }

  private String getSpaceText(String spaceName, String raw) {
    Generator generator = new Generator();
    generator.build(raw);

    Map<String, NameSpace> spaces = generator.getNameSpaces();
    return spaces.get(spaceName).toString();
  }

  private void assertResult(String text) {
    try {
      String content = new String(Files.readAllBytes(Paths.get("generator/test/kkey/generator/tsstub/" + name.getMethodName() + ".ts")));
      Assert.assertEquals(content.trim(), text.trim());
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
