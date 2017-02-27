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
	
	/**
	 * 
	 * @param dim a Dim to be added with this one for purposes of determining an enclosing Dim.
	 * @return a new Dim whose width and height are the greater of those of this dim and
	 * the provided dim.
	 */
	public Dim union(Dim dim) {
	    return new Dim(Math.max(width, dim.width), Math.max(height, dim.height));
	}
	/**
	 * Return a dim that is at least as large in width and height as all of the provided Dims.
	 * @param dims a list of dims, to calculate the enclosing dimension
	 * @return a Dim whose width and height are equal to the greatest width and height of the
	 * provided collection of dims.
	 */
	public static Dim getLargest(Collection<Dim> dims) {
	    Dim largestDim = new Dim(0,0);
	    for(Dim dim: dims) {
	        largestDim = largestDim.union(dim);
	    }
	    return largestDim;
	}
    /**
     * Returns the logarithm of the scale multiplier required to ensure
     * that the second dimension fits within the first, to the given logarithmic base.
     * @param dimToFit the dimension to fit inside this dimension. 
     * @param zoomBase the logarithm base (example 2.0 gives a multiplier of 2 for each integral zoom step.) Values greater than 1 are meaningful.
     * @return the smallest integer that is not larger than the logarithm of the scale
     * required to fit the given dimension inside this dimension, to the given logarithmic base.
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
