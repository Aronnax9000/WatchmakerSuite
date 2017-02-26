package net.richarddawkins.watchmaker.geom;

import java.util.Collection;
import java.util.logging.Logger;

public class Dim {
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.geom.Dim");

	public Dim(int d, int e) {
		this.width = d;
		this.height = e;
	}
	public Dim(double width2, double height2) {
		this((int) width2, (int)height2);
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int width;
	public int height;
	@Override
	public String toString() {
		return "Dim:" + width + "x" + height;
	}
	
	public Dim union(Dim dim) {
	    return new Dim(Math.max(width, dim.width), Math.max(height, dim.height));
	}
	
	public static Dim getLargest(Collection<Dim> dims) {
	    Dim largestDim = new Dim(0,0);
	    for(Dim dim: dims) {
	        largestDim = largestDim.union(dim);
	    }
	    return largestDim;
	}
    /**
     * Returns the binary logarithm of the scale multiplier required to ensure
     * that the second dimension fits within the first.
     * @param boxDim
     * @param largestMorphDim
     * @return
     */
    public int getScale(Dim dimToFit, double zoomBase) {
        
        logger.info("Comparing dim " + this + " to dim-to-fit "
                + dimToFit);
        // > 1 if box is bigger than largest morph (need more positive scale to
        // see morph at all)
        // < 1 if box is smaller than largest morph (need more negative scale to
        // avoid overflow)
        double widthRatio = (double) this.width / dimToFit.width;
        double heightRatio = (double) this.height / dimToFit.height;

        double worst = (widthRatio < heightRatio) ? widthRatio : heightRatio;

        int scale = (int) Math.floor(Math.log(worst) / Math.log(zoomBase));
        logger.info("Worst:" + worst + " scale " + scale);

        return scale;
    }
}
