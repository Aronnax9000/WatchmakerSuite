package net.richarddawkins.watchmaker.morph.mono.geom;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SimpleSwingPic;
import net.richarddawkins.watchmaker.morph.mono.genome.MonochromeGenome;
import net.richarddawkins.watchmaker.morph.util.Globals;

public class MonoPic extends SimpleSwingPic {
  public MonoPic(Morph morph) {
        super(morph);
    }


  @Override
  public void picLine(int x, int y, int xnew, int ynew, int thick, Color color) {
    if (thick > 8) {
      thick = 8;
    }
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
      movePtr.thickness = thick;
      lines.add(movePtr);
      picSize++;
      margin.expandPoint(new Point(x,y), thick);
      margin.expandPoint(new Point(xnew,ynew), thick);
      MonochromeGenome monoGenome = (MonochromeGenome) morph.getGenome(); 
      CompletenessType completenessType = monoGenome.getCompletenessGene().getValue();
      SpokesType spokesType = monoGenome.getSpokesGene().getValue();
      if(completenessType == CompletenessType.Double || spokesType == SpokesType.Radial) {
          margin.expandHorizontal(-x, thick);
          margin.expandHorizontal(-xnew, thick);
      }
      if(spokesType == SpokesType.NSouth || spokesType == SpokesType.Radial) {
          margin.expandVertical(-y, thick);
          margin.expandVertical(-ynew, thick);
      }
    }
  }
  


	  /**
	   * Pic already contains its own origin, meaning the coordinates at which it was originally drawn.
	   * Now draw it at Place
	   */
	  @Override
	  public void drawPic(Graphics2D g2, Point d, Point place, Morph morph) {
	    MonochromeGenome genome = (MonochromeGenome) morph.getGenome();
	    // int j;



	    // To correct initialisation bug, due to call in DoUpdate
	    PicStyleType picStyle = PicStyleType.FF;
	    switch (genome.getCompletenessGene().getValue()) {
	    case Single: {
	      switch (genome.getSpokesGene().getValue()) {
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
	    }
	    case Double:
	      switch (genome.getSpokesGene().getValue()) {
	      case NorthOnly: {
	        picStyle = PicStyleType.FF;
	        break;
	      }
	      case NSouth: {
	        picStyle = PicStyleType.FUD;
	        break;
	      }
	      case Radial: {
	        picStyle = PicStyleType.FUD;
	        break;
	      }
	      }
	    }

	    g2.setStroke(new BasicStroke(Globals.myPenSize));
	    for (Lin line : lines) {
	      actualLine(g2, line, place, picStyle, Compass.NorthSouth);
	      // sometimes rangecheck error
	      
	      if (genome.getSpokesGene().getValue() == SpokesType.Radial) {
	        if (genome.getCompletenessGene().getValue() == CompletenessType.Single) {
	          actualLine(g2, line, place, PicStyleType.RUD, Compass.EastWest);
	        } else {
	          actualLine(g2, line, place, picStyle, Compass.EastWest);
	        }
	      }
	    }
	    g2.setStroke(new BasicStroke(1.0f));
	    // PenSize(1, 1);
	  }

  
  void actualLine(Graphics2D g2, Lin line, Point place, PicStyleType picStyle,
	      Compass orientation) {

	    int y0;
	    int y1;
	    int x0;
	    int x1;
	    int vertOffset;
	    int horizOffset;
	    g2.setStroke(new BasicStroke(line.thickness));
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
	    
	    int mid2 = 2 * place.h;
	    int belly2 = 2 * place.v;

	    
	    switch (picStyle) {
	    case LF:
	      g2.drawLine(x0, y0, x1, y1);
	      break;
	    case RF:
	      g2.drawLine(mid2 - x0, y0, mid2 - x1, y1);
	      break;
	    case FF:
	      g2.drawLine(x0, y0, x1, y1);
	      g2.drawLine(mid2 - x0, y0, mid2 - x1, y1);
	      break;
	    case LUD:
	      g2.drawLine(x0, y0, x1, y1);
	      g2.drawLine(mid2 - x0, belly2 - y0, mid2 - x1, belly2 - y1);
	      break;
	    case RUD:
	      g2.drawLine(mid2 - x0, y0, mid2 - x1, y1);
	      g2.drawLine(x0, belly2 - y0, x1, belly2 - y1);
	      break;
	    case FUD:
	      g2.drawLine(x0, y0, x1, y1);
	      g2.drawLine(mid2 - x0, y0, mid2 - x1, y1);
	      g2.drawLine(x0, belly2 - y0, x1, belly2 - y1);
	      g2.drawLine(mid2 - x0, belly2 - y0, mid2 - x1, belly2 - y1);
	      break;
	    default:
	    }
	  }

}
