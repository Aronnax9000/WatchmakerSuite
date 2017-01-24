package net.richarddawkins.watchmaker.geom;

import java.awt.Dimension;
import java.util.Vector;
/**
 * 
 * @author Alan
 *
 */
public class Boxes  {

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
	
	/**
	 * Find the box within the array containing a particular point.
	 * @param p the point to locate within one of the particular boxes
	 * @param d the size of the overall array of boxes, in pixels.
	 * @return the box number containing the given point.
	 */
	public int getBoxNoContainingPoint(Point p, Dim d) {
		int boxIndex = 0;
		for(Rect box: getBoxes(d)) {
			if(box.contains(p)) {
				return boxIndex;
			}
			boxIndex++;
		}
		return -1;
	}
	/**
	 * For the supplied dimension of the entire array of boxes,
	 * return the size of one (any) box. The returned box width is the dimension
	 * width divided by the number of columns, and the height is the dimension height
	 * divided by the number of rows.
	 * @param dimension the size of the overall array of boxes, in pixels
	 * @return the size of an individual box within the array.
	 */
	public Dim getBoxSize(Dim dimension) {
		return new Dim(dimension.width / cols, dimension.height / rows);
	}
	
	/**
	 * For the given dimension of the entire array of boxes,
	 * return a vector of Rectangles describing the corners of
	 * each box, in row-major order.
	 * @param dimension the size of the overall array of boxes, in pixels.
	 * @return a Vector of Rectangles representing individual boxes, in row-major order.
	 */
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
				boxes.add(new Rect(x, y, x + boxwidth, y + boxheight));
			}
		return boxes;
	}

	/**
	 * Given supplied dimension of the entire array of boxes,
	 * return the midpoint of the nth box (starting with n = 0).
	 * @param dimension the dimensions of the overall array of boxes.
	 * @param boxNo the number of the box to retrieve (row major order)
	 * @return the midpoint of the nth Box.
	 */
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
	/**
	 * For the given dimension of the entire array of boxes,
	 * return a vector of Points describing the midpoints of
	 * each box, in row-major order.
	 * @param dimension the dimension of the entire array of boxes, in pixels
	 * @return a Vector of box midpoints, in row-major order.
	 */
	public Vector<Point> getMidPoints(Dimension dimension) {
		
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
