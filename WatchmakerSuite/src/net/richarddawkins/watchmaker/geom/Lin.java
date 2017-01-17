package net.richarddawkins.watchmaker.geom;

/**
 * Represents a drawing primitive used to draw a biomorph, capable of 
 * rendering any of the classic version biomorphs.
 * <h3>Monochrome</h3>
 * Monochrome Biomorphs are drawn using the most basic shape, Stick.
 * Thickness is variable on a per-Lin basis: each Lin in Monochrome has
 * its own thickness, unlike Colour, where (in the original) every Lin is drawn with the
 * "thickness gene" of the biomorph.
 * <h3>Colour</h3>
 * Colour Biomorphs adds two shape types, Oval and Rectangle, and
 * has two shape fill modes, Open and Filled. A primary color is added.
 * The same color is used for both shape outline tracing and fill. Thickness
 * is also on a per-Lin basis, as in Monochrome above, even though in 
 * classic Colour Biomorphs, all Lins are drawn with the value
 * of the thickness gene.
 * <h3>Snails</h3>
 * <h3>Arthromorphs</h3>
 * Arthromorphs adds the complexity of an alternate fill color to the
 * outline color. If this property is null, drawing routines should
 * default to the primary color of the Lin.
 * @author sven
 *
 */
public class Lin implements Cloneable {
	public Point startPt;
	public Point endPt;
	
	public Lin(Point startPt, Point endPt) {
	    this.startPt = startPt;
	    this.endPt = endPt;
	}
	
    @Override
    public Lin clone() {
        try {
            Lin clone = (Lin) super.clone();
            clone.startPt = startPt;
            clone.endPt = endPt;
          return clone;
        } catch (CloneNotSupportedException e) {
          throw new RuntimeException("this could never happen", e);
        }
      }

    public void expandMargin(Rect margin) {
        margin.expandPoint(startPt, 0);
        margin.expandPoint(endPt, 0);
    }
	
}