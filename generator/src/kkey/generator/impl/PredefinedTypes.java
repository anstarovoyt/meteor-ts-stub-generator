package kkey.generator.impl;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Set;

public class PredefinedTypes {
  private static final HashMap<String, String> TYPES = Maps.newHashMap();
  private static final Set<String> HAS_IMPLICITY_CONSTRUCTOR = Sets.newHashSet();
  private static final Set<String> SKIPPED_FUNCTION = Sets.newHashSet();

  private static final Set<String> GLOBAL_NS = Sets.newHashSet();
  private static final Set<String> PLAIN_MEMBERS = Sets.newHashSet();

  static {
    TYPES.put("Mongo.Collection#find", "Cursor");
    TYPES.put("Tracker.autorun", "Computation");
    TYPES.put("Blaze.Template", "any");
    TYPES.put("Template.instance", "Blaze.TemplateInstance");

    HAS_IMPLICITY_CONSTRUCTOR.add("Tracker.Dependency");

    SKIPPED_FUNCTION.add("Tracker.Computation");

    GLOBAL_NS.add("Template");

    PLAIN_MEMBERS.add("Template");
    PLAIN_MEMBERS.add("MethodInvocation");
    PLAIN_MEMBERS.add("PackageAPI");
    PLAIN_MEMBERS.add("Subscription");
  }

  public static String getType(String fullName) {
    return TYPES.get(fullName);
  }

  public static boolean hasImplConstructor(String fullName) {
    return HAS_IMPLICITY_CONSTRUCTOR.contains(fullName);
  }

  public static boolean isSkippedFunction(String fullName) {
    return SKIPPED_FUNCTION.contains(fullName);
  }

  public static boolean isGlobalNS(String name) {
    return GLOBAL_NS.contains(name);
  }
  public static boolean isPlainMember(String name) {
    return PLAIN_MEMBERS.contains(name);
  }


}
