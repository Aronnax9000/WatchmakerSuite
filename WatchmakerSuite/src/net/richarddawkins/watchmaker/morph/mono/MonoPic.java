package net.richarddawkins.watchmaker.morph.mono;

import java.awt.Color;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.draw.LineTo;
import net.richarddawkins.watchmaker.draw.MoveTo;
import net.richarddawkins.watchmaker.draw.PenSize;
import net.richarddawkins.watchmaker.morph.common.Genome;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.geom.Lin;
import net.richarddawkins.watchmaker.morph.common.geom.Point;
import net.richarddawkins.watchmaker.morph.common.geom.SimplePic;
import net.richarddawkins.watchmaker.morph.util.Globals;

public class MonoPic extends SimplePic {

  public MonoPic() {

  }

  Morph morph;

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
    }
  }


  int mid2;
  int belly2;

  void generatePrimitivesActualLine(Vector<DrawingPrimitive> primitives, 
		  Lin line, Point place, PicStyleType picStyle,
	      Compass orientation) {
	    int y0;
	    int y1;
	    int x0;
	    int x1;
	    primitives.add(new PenSize(line.thickness));
	    if (orientation == Compass.NorthSouth) {
	      y0 = line.startPt.v;
	      y1 = line.endPt.v;
	      x0 = line.startPt.h;
	      x1 = line.endPt.h;
	    } else {
	      y0 = line.startPt.h;
	      y1 = line.endPt.h;
	      x0 = line.startPt.v;
	      x1 = line.endPt.v;
	    }
	    switch (picStyle) {

	    case LF:
	    	primitives.add(new MoveTo(x0, y0));
	    	primitives.add(new LineTo(x1, y1));
		    break;
	  	case RF:
	  		primitives.add(new MoveTo(mid2 - x0, y0));
	  		primitives.add(new LineTo(mid2 - x1, y1));
		    break;
	    case FF:
	    	primitives.add(new MoveTo(x0, y0));
	    	primitives.add(new LineTo(x1, y1));
	    	primitives.add(new MoveTo(mid2 - x0, y0));
	    	primitives.add(new LineTo(mid2 - x1, y1));
	        break;
	    case LUD:
	    	primitives.add(new MoveTo(x0, y0));
	    	primitives.add(new LineTo(x1, y1));
	    	primitives.add(new MoveTo(mid2 - x0, belly2 - y0));
	    	primitives.add(new LineTo(mid2 - x1, belly2 - y1));
	        break;
	    case RUD:
	    	primitives.add(new MoveTo(mid2 - x0, y0));
	    	primitives.add(new LineTo(mid2 - x1, y1));
	    	primitives.add(new MoveTo(x0, belly2 - y0));
	    	primitives.add(new LineTo(x1, belly2 - y1));
	        break;
	    case FUD:
	    	primitives.add(new MoveTo(x0, y0));
	    	primitives.add(new LineTo(x1, y1));
	    	primitives.add(new MoveTo(mid2 - x0, y0));
	    	primitives.add(new LineTo(mid2 - x1, y1));
	    	primitives.add(new MoveTo(x0, belly2 - y0));
	    	primitives.add(new LineTo(x1, belly2 - y1));
	    	primitives.add(new MoveTo(mid2 - x0, belly2 - y0));
	    	primitives.add(new LineTo(mid2 - x1, belly2 - y1));
      break;
		case FSW: // Not in original Pascal source - ABC
			break;
		case LSW: // Not in original Pascal source - ABC
			break;
		case RSW: // Not in original Pascal source - ABC
			break;
		default:
			break;
	    } 
  }

  /**
   * Pic already contains its own origin, meaning the coordinates at which it was originally drawn.
   * Now draw it at Place
   */
  
  public void generatePrimitives(Vector<DrawingPrimitive> primitives, Genome theGenome) {
	MonochromeGenome genome = (MonochromeGenome) theGenome;
    // To correct initialisation bug, due to call in DoUpdate
    PicStyleType picStyle = PicStyleType.FF;
    switch (genome.getCompletenessGene()) {
    case Single: {
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
    }
    case Double:
      switch (genome.getSpokesGene()) {
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
    primitives.addElement(new PenSize(Globals.myPenSize));
    Point place = new Point(0,0);
    mid2 = 2 * place.h;
    belly2 = 2 * place.v;
    for (Lin line : lines) {
      generatePrimitivesActualLine(primitives, line, place, picStyle, Compass.NorthSouth);
      // sometimes rangecheck error
      if (genome.getSpokesGene() == SpokesType.Radial) {
        if (genome.getCompletenessGene() == CompletenessType.Single) {
        	generatePrimitivesActualLine(primitives, line, place, PicStyleType.RUD, Compass.EastWest);
        } else {
        	generatePrimitivesActualLine(primitives, line, place, picStyle, Compass.EastWest);
        }
      }
    }
    primitives.add(new PenSize(1.0f));
    // PenSize(1, 1);
  }


}