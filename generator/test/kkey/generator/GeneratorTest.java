package kkey.generator;

import kkey.generator.impl.Generator;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GeneratorTest {
  public static final String DEFAULT_MODULE = "Meteor";

  @Rule
  public TestName name = new TestName();

  @Test
  public void testSimple() throws IOException {
    doTestJSON();
  }

  @Test
  public void testFieldSimpleMember() {
    doTestJSON();
  }

  private void doTestJSON() {
    try {
      Generator generator = new Generator();
      generator.process(getText(".json"), true);
      assertResult(generator.getTypeScriptStub());
    }
    catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @Test
  public void testTwoFieldSimpleMember() {
    doTestJSON();
  }


  @Test
  public void testSessionNameSpaceParse() {
    doTestJSON();
  }

  @Test
  public void testAccountsNameSpace() {
    doTestJSON();
  }

  @Test
  public void testVarArgs() {
    doTestJSON();
  }

  @Test
  public void testNamespacesOnly() {
    doTestJSON();
  }

  @Test
  public void testParseMongoNamespace() {
    doTestJSON();
  }

  @Test
  public void testParseMeteorNameSpace() {
    doTestJSON();
  }

  @Test
  public void test094() {
    doTestJSON();
  }

  @Test
  public void test102() {
    doTestJSON();
  }

  @Test
  public void testSubString() {
    String str = "var a = {subName};";
    Assert.assertEquals("{subName}", str.substring(str.indexOf('{'), str.indexOf('}') +1));
  }

  private String getText(String ext) throws IOException {
    return new String(Files.readAllBytes(Paths.get("generator/test/kkey/generator/files/" + name.getMethodName() + ext)));
  }

  private void assertResult(String text) {
    try {
      String content = new String(Files.readAllBytes(Paths.get("generator/test/kkey/generator/files/" + name.getMethodName() + ".ts")));
      Assert.assertEquals(content, text.replaceAll("\\s+$", ""));
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}