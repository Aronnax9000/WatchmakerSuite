package net.richarddawkins.wm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.imageio.ImageIO;

public class ClassicImageLoader {

  public static String[] imageNames = { 
      "BulletChar165_ALAN_0015_5x5",
      "AboutArthromorphs_PICT_00001_282x107",
      "AboutBlindWatchmaker_PICT_26817_463x287", "AboutColourWatchmaker_PICT_00257_486x352",
      "AboutSnailsSnikwad_PICT_00002_145x113", "AlertCautionAlertbw_cicn_00002_32x32",
      "AlertCautionAlert_cicn_00002_32x32", "AlertNoteAlertbw_cicn_00001_32x32",
      "AlertNoteAlert_cicn_00001_32x32", "AlertStopAlertbw_cicn_00000_32x32",
      "AlertStopAlert_cicn_00000_32x32", "Arthromorph_ALAN_00010_32x32",
      "ArthromorphEngineering_ALAN_00004_342x389", "ArthromorphPreferences_ALAN_00008_265x211",
      "BiomorphSaysOK_PICT_09453_100x57", "BWSpiderLogoBlue_icl4_23096_32x32",
      "BWSpiderLogoMono_ICNO_23096_32x32", "BWSpiderLogoPurple_icl8_23096_32x32",
      "BWTreeLogoBlueThin_icl4_17669_32x32", "BWTreeLogoBlueThin_icl8_17669_32x32",
      "BWTreeLogoMonoThick_ICNO_00128_32x32", "BWTreeLogoMonoThin_ICNO_17669_32x32",
      "BWTreeLogoMonoThin_ICON_17669_32x32", "BWZooLogo_icl4_24987_32x32",
      "BWZooLogo_icl8_24987_32x32", "BWZooLogo_ICNO_24987_32x32",
      "ColourTimingDialog_ALAN_00003_366x312", "CursorBirdFlippingFingerHand_CURS_00128_48x16",
      "CursorBirdFlippingFingerHand_CURS_00129_48x16", "CursorBlackMonolith_CURS_00142_48x16",
      "CursorBlank_CURS_00151_48x16", "CursorBreed_CURS_00145_48x16",
      "CursorCentreDie_CURS_00144_48x16", "CursorCentreQuestion_CURS_00141_48x16",
      "CursorCentreRect_CURS_00143_48x16", "CursorDie_CURS_15208_48x16",
      "CursorDownArrow_CURS_00138_48x16", "CursorGrabbyHand_CURS_00256_48x16",
      "CursorGun_CURS_00149_48x16", "CursorHypodermic_CURS_00140_48x16",
      "CursorLeftArrow_CURS_00135_48x16", "CursorMagnifyingGlass_CURS_00150_48x16",
      "CursorPedigreeMaybe_CURS_00147_48x16", "CursorPointyHand_CURS_00146_48x16",
      "CursorRightArrow_CURS_00136_48x16", "CursorScissors_CURS_00148_48x16",
      "CursorUpArrow_CURS_00137_48x16", "CursorUpperEquals_CURS_00139_48x16",
      "CursorWatch_CURS_-15776_48x16", "Hypodermic_PICT_03937_16x16",
      "IconBreedingGridIcon_ICON_00256_32x32", "IconFlipBirdToBreedingGrid_ICON_00261_32x32",
      "IconSettingsOrDialogOrMenuIcon_ICON_00260_32x32", "MonoBiomorph_ics4_17669_16x16",
      "MonoBiomorph_ics8_17669_16x16", "MonoBiomorph_icsO_17669_16x16",
      "SixSidedDieShowsFiveIcon_ICON_00257_32x32", "SixSidedDieShowsFiveIcon_ICON_00258_32x32",
      "SmallLeftTriangle_ICON_23718_32x32", "SmallRightTriangle_ICON_23717_32x32",
      "SnailDialogArray_ALAN_00129_495x287", "SnailDialogCustom_ALAN_00128_564x324",
      "SnailDialogHelp_ALAN_11916_429x331", "SnailDialogPriorities_ALAN_00135_397x342",
      "SnailDoubleLogo_icl8_23096_32x32", "SnailDoubleLogo_icl8_24987_32x32",
      "SnailDoubleLogo_ICNO_23096_32x32", "SnailDoubleLogo_ICNO_24987_32x32",
      "SnailLogoBlackBackground_icl4_17669_32x32", "SnailLogoBlackBackground_icl4_23096_32x32",
      "SnailLogoBlackBackground_icl4_24987_32x32", "SnailLogo_icl8_17669_32x32",
      "SnailLogo_ICNO_17669_32x32", "SnailLogo_ics4_17669_16x16", "SnailLogo_ics8_17669_16x16",
      "SnailLogo_ics8_23096_16x16", "SnailLogo_ics8_24987_16x16", "SnailLogo_icsO_17669_16x16",
      "SnailLogo_icsO_23096_16x16", "SnailLogo_icsO_24987_16x16",
      "SnailOutline128ConeWhelk_PICT_00128_113x171",
      "SnailOutline130JapaneseWonder_PICT_00130_105x176", "SnailOutline132Rapa_PICT_00132_104x181",
      "SnailOutline134FigTun_PICT_00134_77x212", "SnailOutline136Gallaghers_PICT_00136_78x141",
      "SnailOutline138RazorShell_PICT_00138_46x214", "SnailOutline140Unnamed_PICT_00140_122x145",
      "SnailOutline142Unnamed_PICT_00142_78x181", "SnailOutline144Unnamed_PICT_00144_164x201",
      "SnailOutline146Eloise_PICT_00146_91x172", "SnailOutline148Scallop_PICT_00148_232x231",
      "SnailOutline150Lightning_PICT_00150_57x173", "SnailOutline152Sundial_PICT_00152_259x171",
      "SnailOutline154Unnamed_PICT_00154_68x173", "SnailSnapshotSnikwad_PICT_30553_145x242",
      "SnailOutline128ConeWhelkARGB_PICT_00128_113x171",
      "SnailOutline130JapaneseWonderARGB_PICT_00130_105x176",
      "SnailOutline132RapaARGB_PICT_00132_104x181",
      "SnailOutline134FigTunARGB_PICT_00134_77x212",
      "SnailOutline136GallaghersARGB_PICT_00136_78x141",
      "SnailOutline138RazorShellARGB_PICT_00138_46x214",
      "SnailOutline140UnnamedARGB_PICT_00140_122x145",
      "SnailOutline142UnnamedARGB_PICT_00142_78x181",
      "SnailOutline144UnnamedARGB_PICT_00144_164x201",
      "SnailOutline146EloiseARGB_PICT_00146_91x172",
      "SnailOutline148ScallopARGB_PICT_00148_232x231",
      "SnailOutline150LightningARGB_PICT_00150_57x173",
      "SnailOutline152SundialARGB_PICT_00152_259x171",
      "SnailOutline154UnnamedARGB_PICT_00154_68x173",
  };
  static Vector<ClassicImage> classicImages = new Vector<ClassicImage>();
  static HashMap<String, ClassicImage> imageMap = new HashMap<String, ClassicImage>();

  static HashMap<String, HashMap<String, ClassicImage>> resources = new HashMap<String, HashMap<String, ClassicImage>>();

  static void add(ClassicImage image) {
    HashMap<String, ClassicImage> resourceIdMap = resources.get(image.resourceType);
    if (resourceIdMap == null) {
      resourceIdMap = new HashMap<String, ClassicImage>();
      resources.put(image.resourceType, resourceIdMap);
    }
    resourceIdMap.put(image.resourceId, image);
    imageMap.put(image.imageName, image);
  }

  static public ClassicImage getPicture(String resourceType, String resourceId) {
    return resources.get(resourceType).get(resourceId);
  }

  static public ClassicImage getPicture(String imageName) {
    return imageMap.get(imageName);
  }

  static public ClassicImage getGeneratingCurve(int index) {
    switch(index) {
    case 128: return getPicture("SnailOutline128ConeWhelkARGB_PICT_00128_113x171");
    case 130: return getPicture("SnailOutline130JapaneseWonderARGB_PICT_00130_105x176");
    case 132: return getPicture("SnailOutline132RapaARGB_PICT_00132_104x181");
    case 134: return getPicture("SnailOutline134FigTunARGB_PICT_00134_77x212");
    case 136: return getPicture("SnailOutline136GallaghersARGB_PICT_00136_78x141");
    case 138: return getPicture("SnailOutline138RazorShellARGB_PICT_00138_46x214");
    case 140: return getPicture("SnailOutline140UnnamedARGB_PICT_00140_122x145");
    case 142: return getPicture("SnailOutline142UnnamedARGB_PICT_00142_78x181");
    case 144: return getPicture("SnailOutline144UnnamedARGB_PICT_00144_164x201");
    case 146: return getPicture("SnailOutline146EloiseARGB_PICT_00146_91x172");
    case 148: return getPicture("SnailOutline148ScallopARGB_PICT_00148_232x231");
    case 150: return getPicture("SnailOutline150LightningARGB_PICT_00150_57x173");
    case 152: return getPicture("SnailOutline152SundialARGB_PICT_00152_259x171");
    case 154: return getPicture("SnailOutline154UnnamedARGB_PICT_00154_68x173");
    default: return null;
    }
  }
  // SnailOutline128ConeWhelk_PICT_00128_113x171
  // SnailOutline128ConeWhelkARGB_PICT_00128_113x171
  static {
    for (String imageName : imageNames) {
      System.out.println("Attempting to load image " + imageName);
      try {
        ClassicImage classicImage = new ClassicImage(
            ImageIO.read(ClassicImageLoader.class
                .getResource("/net/richarddawkins/watchmaker/img/" + imageName + ".png")),
            imageName);
        classicImages.add(classicImage);
        ClassicImageLoader.add(classicImage);
      } catch (IOException e) {
        System.err.println("Error while trying to load " + imageName + ": " + e.getMessage());
      }
    }
  }

  public static void main(String[] args) {
    for (ClassicImage image : classicImages) {
      String res = image.getImage().getWidth() + "x" + image.getImage().getHeight();
      System.out.println(image.description + " " + image.resolution + " " + res + " "
          + res.equals(image.resolution));
      if (!res.equals(image.resolution))
        System.out.println("************************ERROR!!!!");
    }
    System.out.println("Vector size " + classicImages.size());
  }
}
