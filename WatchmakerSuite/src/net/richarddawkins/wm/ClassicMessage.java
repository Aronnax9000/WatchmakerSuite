package net.richarddawkins.wm;

import java.util.HashMap;
import java.util.Vector;

public class ClassicMessage {
  String resourceType;
  String resourceId;
  static final Vector<ClassicMessage> messages = new Vector<ClassicMessage>();
  static final HashMap<String, HashMap<String, ClassicMessage>> messageMap = new HashMap<String, HashMap<String, ClassicMessage>>();

  static public ClassicMessage getMessge(String resourceType, String resourceId) {
    return messageMap.get(resourceType).get(resourceId);
  }

  static void add(ClassicMessage message) {
    HashMap<String, ClassicMessage> resourceIdMap = messageMap.get(message.resourceType);
    if (resourceIdMap == null) {
      resourceIdMap = new HashMap<String, ClassicMessage>();
      messageMap.put(message.resourceType, resourceIdMap);
    }
    resourceIdMap.put(message.resourceId, message);
  }
}