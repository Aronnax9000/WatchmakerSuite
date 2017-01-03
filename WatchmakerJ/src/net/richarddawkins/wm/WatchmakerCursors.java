package net.richarddawkins.watchmaker;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;

public class WatchmakerCursors {
  static Toolkit toolkit = Toolkit.getDefaultToolkit();
  static Point hotspot = new Point(8,8);
  public static final Cursor leftArrow = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture(
          "CursorLeftArrow_CURS_00135_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Left arrow");
  public static final Cursor rightArrow = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture("CursorRightArrow_CURS_00136_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Right arrow");
  public static final Cursor upArrow = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture("CursorUpArrow_CURS_00137_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Up arrow");
  public static final Cursor downArrow = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture("CursorDownArrow_CURS_00138_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Down arrow");
  public static final Cursor equalsArrow = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture("CursorUpperEquals_CURS_00139_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Equals");
  public static final Cursor watchCursor = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture("CursorWatch_CURS_-15776_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Watch");
}
