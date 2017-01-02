package net.richarddawkins.watchmaker.morph.colour;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.morph.common.Genome;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.geom.Lin;
import net.richarddawkins.watchmaker.morph.common.geom.Point;
import net.richarddawkins.watchmaker.morph.common.geom.SimplePic;
import net.richarddawkins.watchmaker.morph.mono.CompletenessType;
import net.richarddawkins.watchmaker.morph.mono.SpokesType;

public class ColourPic extends SimplePic {
  public ColourPic() {
  }

  int[] colorVals = { 0, 51, 102, 153, 204, 255 };
  int[] backColorVals = { 255, 204, 153, 102, 51, 0 };
  static Color[] rgbColorPalette = new Color[256];

  static {
    // First 216 colors
    for (int r = 0; r < 6; r++)
      for (int g = 0; g < 6; g++)
        for (int b = 0; b < 6; b++)
          rgbColorPalette[r * 36 + g * 6 + b] = new Color(255 - (5 - r) * 51, 255 - (5 - g) * 51,
              255 - (5 - b) * 51);
    for (int i = 216; i < 216 + 10; i++)
      rgbColorPalette[i] = new Color((10 - (i - 216)) * 25, 0, 0);
    for (int i = 226; i < 226 + 10; i++)
      rgbColorPalette[i] = new Color(0, (10 - (i - 226)) * 25, 0);
    for (int i = 236; i < 236 + 10; i++)
      rgbColorPalette[i] = new Color(0, 0, (10 - (i - 236)) * 25);
    for (int i = 246; i < 246 + 10; i++)
      rgbColorPalette[i] = new Color((10 - (i - 246)) * 25, (10 - (i - 246)) * 25,
          (10 - (i - 246)) * 25);
  }

  void limb(Graphics2D g2, int x0, int y0, int x1, int y1, ColourBiomorph morph) {
    Rectangle square = null;
    ColourGenome genome = (ColourGenome) morph.getGenome();
    if (genome.getLimbShapeGene() == LimbType.Oval
        || genome.getLimbShapeGene() == LimbType.Rectangle)
      square = new Rectangle(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x1 - x0),
          Math.abs(y1 - y0));

    g2.setStroke(new BasicStroke(genome.getThicknessGene()));
    switch (genome.getLimbShapeGene()) {
    case Oval:
      g2.drawOval(square.x, square.y, square.width, square.height);
      if (genome.getLimbFillGene() == LimbFillType.Filled)
        g2.fillOval(square.x, square.y, square.width, square.height);
      break;
    case Rectangle:
      g2.drawRect(square.x, square.y, square.width, square.height);
      if (genome.getLimbFillGene() == LimbFillType.Filled)
        g2.fillRect(square.x, square.y, square.width, square.height);
      break;
    default:
      break;
    }
    g2.drawLine(x0, y0, x1, y1);
    g2.setStroke(new BasicStroke(1.0f)); // Was MyPenSize
  }

  /**
   * FIXME
   * 
   * @param colorIndex
   * @return
   */
  static Color chooseColor(int colorIndex) {
    return rgbColorPalette[colorIndex];
  }

  void actualLine(Graphics2D g2, PicStyleType picStyle, Compass orientation, Lin line, Point place,
      ColourBiomorph morph, int mid2, int belly2) {
    // int linColor;
    int vertOffset;
    int horizOffset;
    int x0, x1, y0, y1;

    if (orientation == Compass.NorthSouth) {
      vertOffset = origin.v - place.v;
      horizOffset = origin.h - place.h;
      y0 = line.startPt.v - vertOffset;
      y1 = line.endPt.v - vertOffset;
      x0 = line.startPt.h - horizOffset;
      x1 = line.endPt.h - horizOffset;
    } else {
      vertOffset = origin.h - place.v;
      horizOffset = origin.v - place.h;
      y0 = line.startPt.h - vertOffset;
      y1 = line.endPt.h - vertOffset;
      x0 = line.startPt.v - horizOffset;
      x1 = line.endPt.v - horizOffset;
    }
    g2.setColor(line.color);
    switch (picStyle) {
    case LF:
      limb(g2, x0, y0, x1, y1, morph);
    case RF:
      limb(g2, mid2 - x0, y0, mid2 - x1, y1, morph);
    case FF:
      limb(g2, x0, y0, x1, y1, morph);
      limb(g2, mid2 - x0, y0, mid2 - x1, y1, morph);
    case LUD:
      limb(g2, x0, y0, x1, y1, morph);
      limb(g2, mid2 - x0, belly2 - y0, mid2 - x1, belly2 - y1, morph);
    case RUD:
      limb(g2, mid2 - x0, y0, mid2 - x1, y1, morph);
      limb(g2, x0, belly2 - y0, x1, belly2 - y1, morph);
    case FUD:
      limb(g2, x0, y0, x1, y1, morph);
      limb(g2, mid2 - x0, y0, mid2 - x1, y1, morph);
      limb(g2, x0, belly2 - y0, x1, belly2 - y1, morph);
      limb(g2, mid2 - x0, belly2 - y0, mid2 - x1, belly2 - y1, morph);
    default:
      break;
    }
  }

  public void picLine(int x, int y, int xnew, int ynew, int thickness, Color color) {

    if (picSize >= PICSIZEMAX) {
      // {Message(GetString(TooLargeString));}
      // {used the help dialog! v1.1 changed to alert}
      // DisplayError(-147, 'Biomorph too large, or other problem', '
      // ', StopError);
      // ExitToShell
    } else {
      Lin movePtr = new Lin();
      movePtr.startPt.h = x;
      movePtr.startPt.v = y;
      movePtr.endPt.h = xnew;
      movePtr.endPt.v = ynew;
      movePtr.color = color;
      lines.add(movePtr);
      picSize++;
    }
  }

  /**
   * Pic already contains its own origin, meaning the coordinates at which it was originally drawn.
   * Now draw it at Place
   * 
   * @param place
   * @param biomorph
   */

  public void drawPic(Graphics2D g2, Dimension d, Point place, Morph morph) {
    
	  ColourGenome genome = (ColourGenome) morph.getGenome();
    g2.setColor(ColourPic.chooseColor((int) genome.getBackColorGene()));
    g2.fillRect(0, 0, d.width, d.height);
    PicStyleType picStyle;
    picStyle = PicStyleType.FF; // To correct initialisation bug, due to call in Update
    switch (genome.getCompletenessGene()) {
    case Single:
      switch (genome.getSpokesGene()) {
      case NorthOnly:
        picStyle = PicStyleType.LF;
        break;
      case NSouth:
        picStyle = PicStyleType.LUD;
        break;
      case Radial:
        picStyle = PicStyleType.LUD;
        break;
      }
      break;
    case Double:
      switch (genome.getSpokesGene()) {
      case NorthOnly:
        picStyle = PicStyleType.FF;
        break;
      case NSouth:
        picStyle = PicStyleType.FUD;
        break;
      case Radial:
        picStyle = PicStyleType.FUD;
        break;
      }
      break;
    }
    // PenSize(MyPenSize, MyPenSize);
    g2.setStroke(new BasicStroke(1.0f));
    int mid2 = 2 * place.h;
    int belly2 = 2 * place.v;
    for (Lin line : lines) {

      // sometimes rangecheck error
      actualLine(g2, picStyle, Compass.NorthSouth, line, place, (ColourBiomorph) morph, mid2,
          belly2);
      if (genome.getSpokesGene() == SpokesType.Radial) {
        if (genome.getCompletenessGene() == CompletenessType.Single)
          actualLine(g2, PicStyleType.RUD, Compass.EastWest, (Lin) line, place,
              (ColourBiomorph) morph, mid2, belly2);
        else
          actualLine(g2, picStyle, Compass.EastWest, (Lin) line, place, (ColourBiomorph) morph,
              mid2, belly2);
      }
    }

    g2.setStroke(new BasicStroke(1.0f));
    g2.setColor(Color.BLACK);
  }

@Override
public void generatePrimitives(Vector<DrawingPrimitive> primitives, Genome genome) {
	// TODO Auto-generated method stub
	
}
}
