package kkey.generator;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import kkey.generator.impl.Generator;

import javax.xml.bind.DatatypeConverter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GitHubAgent {
  public static final String TAG_URL = "https://api.github.com/repos/meteor/meteor/git/refs/tags";
  public static final String SOURCE_URL = "https://api.github.com/repos/meteor/meteor/contents/docs/client/data.js?ref=release/";

  public static final JsonParser JSON_PARSER = new JsonParser();
  public static final String REFS_TAGS_RELEASE = "refs/tags/release/";
  public static final String START_STRING = "METEOR@";

  public static void main(String[] args) throws Exception {
    getReleaseToUrl();
  }


  public static void getReleaseToUrl() throws Exception {
    URL url = new URL(TAG_URL);
    URLConnection conn = url.openConnection();
    JsonElement parse = JSON_PARSER.parse(new InputStreamReader(conn.getInputStream()));

    assert parse.isJsonArray();
    for (JsonElement element : parse.getAsJsonArray()) {
      JsonElement ref = element.getAsJsonObject().get("ref");
      String refAsString = ref.getAsString();
      boolean hasStart = false;
      if (refAsString.startsWith(REFS_TAGS_RELEASE + START_STRING)) {
        refAsString = REFS_TAGS_RELEASE + refAsString.substring(REFS_TAGS_RELEASE.length() + START_STRING.length());
        hasStart = true;
      }
      if (refAsString.startsWith(REFS_TAGS_RELEASE)) {
        String exactRelease = refAsString.substring(REFS_TAGS_RELEASE.length());

        System.out.println(exactRelease);
        //String[] split = exactRelease.split("\\.");
        //int parseStart1 = Integer.parseInt(split[1]);
        //int parseStart0 = Integer.parseInt(split[0]);
        //int parseStart2 = split.length > 2 ? Integer.parseInt(split[2]) : 0;
        if (!exactRelease.equals("1.2-rc.4")) {
          continue;
        }
        System.out.println("will really process " + exactRelease);
        URL urlToSource = new URL(SOURCE_URL + (hasStart ? START_STRING : "") + exactRelease);
        System.out.println(urlToSource);
        URLConnection connToSource = urlToSource.openConnection();
        JsonElement parseSource = JSON_PARSER.parse(new InputStreamReader(connToSource.getInputStream()));
        byte[] contents = DatatypeConverter.parseBase64Binary(parseSource.getAsJsonObject().get("content").getAsString());
        printFile(new String(contents), exactRelease);
      }
    }
  }

  public static void printFile(String content, String version) {
    try {
      Generator generator = new Generator();

      String json = content.substring(content.indexOf('{'), content.lastIndexOf('}') + 1);

      generator.process(json, true);

      Path path = Paths.get("stubs/meteor-v" + version + ".d.ts");
      if (Files.exists(path)) {
        Files.delete(path);
      }

      Path file = Files.createFile(path);

      OutputStream stream = Files.newOutputStream(file);
      stream.write(generator.getTypeScriptStub().getBytes());
    }
    catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }


  public static boolean isCompletedNumber(String fullRef) {
    for (char c : fullRef.substring(REFS_TAGS_RELEASE.length()).toCharArray()) {
      if ((!Character.isDigit(c) && (c != '.'))) {
        return false;
      }
    }

    return true;
  }
}
