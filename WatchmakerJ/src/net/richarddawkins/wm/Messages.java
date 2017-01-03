package net.richarddawkins.watchmaker;

import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
  private static ResourceBundle messages = null;

  public static ResourceBundle getMessages() {
    if (messages == null)
      messages = ResourceBundle.getBundle("net.richarddawkins.watchmaker.strings.watchmaker",
          Locale.getDefault());
    return messages;
  }
}
