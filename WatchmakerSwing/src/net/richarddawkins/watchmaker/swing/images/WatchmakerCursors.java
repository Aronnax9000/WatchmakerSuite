package net.richarddawkins.watchmaker.swing.images;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

public class WatchmakerCursors {
  public static Cursor createCustomCursor(Image image) {
        return toolkit.createCustomCursor(image, hotspot, "morph");
    }
  static Toolkit toolkit = Toolkit.getDefaultToolkit();
  static Point hotspot = new Point(4,4);
  public static final Cursor leftArrow = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture(
          "CursorLeftArrow_CURS_00135_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Left arrow");
  
  public static final Cursor magnify = toolkit.createCustomCursor(
          ClassicImageLoader.getPicture("CursorMagnifyingGlass_CURS_00150_48x16").getImage().getSubimage(0, 0, 16, 16), 
          hotspot, "Magnify");
  public static final Cursor kill = toolkit.createCustomCursor(
          ClassicImageLoader.getPicture("CursorGun_CURS_00149_48x16").getImage().getSubimage(0, 0, 16, 16), 
          hotspot, "Kill");
  public static final Cursor move = toolkit.createCustomCursor(
          ClassicImageLoader.getPicture("CursorPointyHand_CURS_00146_48x16").getImage().getSubimage(0, 0, 16, 16), 
          hotspot, "Move");
  public static final Cursor detach = toolkit.createCustomCursor(
          ClassicImageLoader.getPicture("CursorScissors_CURS_00148_48x16").getImage().getSubimage(0, 0, 16, 16), 
          hotspot, "Detach");
  public static final Cursor rightArrow = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture("CursorRightArrow_CURS_00136_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Right arrow");
  public static final Cursor upArrow = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture("CursorUpArrow_CURS_00137_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Up arrow");
  public static final Cursor downArrow = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture("CursorDownArrow_CURS_00138_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Down arrow");
  public static final Cursor equalsSign = toolkit.createCustomCursor(
      ClassicImageLoader.getPicture("CursorUpperEquals_CURS_00139_48x16").getImage().getSubimage(0, 0, 16, 16), 
      hotspot, "Equals");
  public static final Cursor watchCursor = toolkit.createCustomCursor(
	      ClassicImageLoader.getPicture("CursorWatch_CURS_-15776_48x16").getImage().getSubimage(0, 0, 16, 16), 
	      hotspot, "Watch");
  public static final Cursor hypodermic = toolkit.createCustomCursor(
	      ClassicImageLoader.getPicture("CursorHypodermic_CURS_00140_48x16").getImage().getSubimage(0, 0, 16, 16), 
	      hotspot, "Hypodermic");
  public static final Cursor breed = toolkit.createCustomCursor(
	      ClassicImageLoader.getPicture("CursorBreed_CURS_00145_48x16").getImage().getSubimage(0, 0, 16, 16), 
	      hotspot, "Breed");
  public static final Cursor random = toolkit.createCustomCursor(
	      ClassicImageLoader.getPicture("CursorCentreDie_CURS_00144_48x16").getImage().getSubimage(16, 0, 16, 16), 
	      hotspot, "Random");
  public static final Cursor highlight = toolkit.createCustomCursor(
	      ClassicImageLoader.getPicture("CursorBlackMonolith_CURS_00142_48x16").getImage().getSubimage(16, 0, 16, 16), 
	      hotspot, "Highlight");
public static final Cursor pedigree = toolkit.createCustomCursor(
        ClassicImageLoader.getPicture("CursorPedigreeMaybe_CURS_00147_48x16").getImage().getSubimage(16, 0, 16, 16), 
        hotspot, "Pedigree");
  
}
