package net.richarddawkins.watchmaker.morphs.bio.geom;

import java.util.Vector;

import net.richarddawkins.watchmaker.geom.Phenotype;
import net.richarddawkins.watchmaker.geom.Rect;
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
public abstract class Pic implements Phenotype {

    /**
     * PICSIZEMAX is 4 * 4095, which is 4 times the original (Pascal) value, since the
     * current implementation stores each component of a symmetry separately, instead
     * of calculating them as they're drawn.
     */
    public final static int PICSIZEMAX = 4* 4095;

    public Vector<Lin> lines = new Vector<Lin>();
    public Rect margin = new Rect();

    public Morph morph;
    public Pic() {
        super();
    }


    public int size() { return lines.size(); }
    public void addLin(Lin lin) {
        lines.add(lin);
    }
    
    public void zeroPic() {
        lines.clear();
    }

}