package kkey.generator;

import kkey.generator.impl.Generator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GeneratorTest {
  @Rule
  public TestName name = new TestName();

  @Test
  public void testSimple() throws IOException {
    Generator generator = new Generator();
    generator.process(getText(".json"), true);
  }

  private String getText(String ext) throws IOException {
    return new String(Files.readAllBytes(Paths.get("generator/test/kkey/generator/files/" + name.getMethodName() + ext)));
  }
}
