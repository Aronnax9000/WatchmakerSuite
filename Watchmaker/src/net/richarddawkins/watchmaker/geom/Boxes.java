package net.richarddawkins.watchmaker.geom;

import java.util.Vector;
import java.util.logging.Logger;
/**
 * 
 * @author Alan
 *
 */
public class Boxes extends BoxManager  {
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.geom.Boxes");
	@Override
	public int getBoxCount() {
		return boxCount;
	}
	@Override
	public int getMidBox() {
		return midBox;
	}
	public final int cols;
	public final int rows;
	public final int boxCount;
	public final int midBox;

	/**
	 * Construct a cols * rows array of boxes.
	 * @param cols the number of columns in the box array.
	 * @param rows the number of rows in the box array.
	 */
	public Boxes(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
		this.boxCount = cols * rows;
		this.midBox = boxCount / 2;	
	}
	


	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.geom.BoxManager#getBoxSize(net.richarddawkins.watchmaker.geom.Dim)
	 */
	@Override
	public Dim getBoxSize(Dim dimension) {
		return new Dim(dimension.width / cols, dimension.height / rows);
	}
	
	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.geom.BoxManager#getBoxes(net.richarddawkins.watchmaker.geom.Dim)
	 */
	@Override
	public Vector<Rect> getBoxes(Dim dimension)
	{
		Vector<Rect> boxes = new Vector<Rect>();
		int boxwidth = (int) dimension.width / cols;
		int boxheight = (int) dimension.height / rows;
		for(int j = 0; j < rows; j++)
			for(int i = 0; i < cols; i++)
			{
				int x = i * boxwidth;
				int y = j * boxheight;
				Rect rect = new Rect(x, y, x + boxwidth, y + boxheight);
				logger.fine("GetBoxes " + (i + j*cols) + ": " + rect.toString());
				boxes.add(rect);
			}
		return boxes;
	}

	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.geom.BoxManager#getMidPoint(net.richarddawkins.watchmaker.geom.Dim, int)
	 */
	@Override
	public Point getMidPoint(Dim dimension, int boxNo) {
		int col = boxNo % cols;
		int row = boxNo / cols;
		int boxwidth = dimension.width / cols;
		int boxheight = dimension.height / rows;
		int halfboxwidth = boxwidth / 2;
		int halfboxheight = boxheight / 2;
		return new Point(boxwidth * col + halfboxwidth,
				boxheight * row + halfboxheight);
		
	}
	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.geom.BoxManager#getMidPoints(net.richarddawkins.watchmaker.geom.Dim)
	 */
	@Override
	public Vector<Point> getMidPoints(Dim dimension) {
		
		int boxwidth = dimension.width / cols;
		int boxheight = dimension.height / rows;
		int halfboxwidth = boxwidth / 2;
		int halfboxheight = boxheight / 2;

		Vector<Point> midPoints = new Vector<Point>();
		for(int j = 0; j < rows; j++)
			for(int i = 0; i < cols; i++)
			{
				int x = i * boxwidth + halfboxwidth;
				int y = j * boxheight + halfboxheight;
				midPoints.add(new Point(x, y));
			}
		return midPoints;
	}
	

}
