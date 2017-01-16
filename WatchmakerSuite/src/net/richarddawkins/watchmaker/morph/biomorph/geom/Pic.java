package net.richarddawkins.watchmaker.morph.biomorph.geom;

import java.awt.Color;
import java.util.Vector;

import net.richarddawkins.watchmaker.morph.Morph;

/**
 * <h1>Pic</h1>
 * <p>
 * A Pic represents an ordered list of drawing primitives, generally lines (the
 * class Lin)
 * </p>
 * <h2>Original sources</h2>
 * <h3>Monochrome WatchMaker/Globals (253:259)</h3>
 * 
 * <pre>
 * 	Pic = RECORD
 *   BasePtr: Ptr;
 *   MovePtr: LinPtr;
 *   Origin: Point;
 *   PicSize: Integer;
 *   PicPerson: person
 *   END;
 * </pre>
 * 
 * <h3>Colour Watchmaker/Common_Exhibition.p (280:286)</h3>
 * 
 * <pre>
 * 	Pic = record
 * 	 BasePtr: Ptr;
 * 	 MovePtr: LinPtr;
 * 	 Origin: Point;
 * 	 PicSize: INTEGER;
 * 	 PicPerson: person
 * 	end;
 * </pre>
 * 
 * <h3>Snails/Globals (280:286)</h3>
 * 
 * <pre>
 * 	Pic = RECORD
 *   BasePtr: Ptr;
 *   MovePtr: LinPtr;
 *   Origin: Point;
 *   PicSize: Integer;
 *   PicPerson: person
 *  END;
 * </pre>
 * 
 * @author alan
 *
 */
public abstract class Pic {
    public enum Compass {
        EastWest, NorthSouth
    }

    public enum PicStyleType {
        FF, FSW, FUD, LF, LSW, LUD, RF, RSW, RUD
    }

    /**
     * PICSIZEMAX is 4 * 4095, which is 4 times the original (Pascal) value, since the
     * current implementation stores each component of a symmetry separately, instead
     * of calculating them as they're drawn.
     */
    public final static int PICSIZEMAX = 4* 4095;

    public Vector<Lin> lines = new Vector<Lin>();
    public Rect margin = new Rect();

    public Morph morph;
    public Point origin;

    public Pic() {
        super();
    }

    abstract public void picLine(int xx1, int yy1, int xx2, int yy2);

    abstract public void picLine(int xx1, int yy1, int xx2, int yy2, Color color);

    abstract public void picLine(int xx1, int yy1, int xx2, int yy2, int thick);

    abstract public void picLine(int x, int y, int xnew, int ynew, int thickness, Color color);

    public void zeroPic(Point here) {
        origin = here;
        lines.clear();
    }

}