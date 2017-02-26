package net.richarddawkins.watchmaker.geom;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

/**
 * 
 * @author Alan
 *
 */
public class GridBoxManager extends BoxManager {
    
    private static Logger logger = Logger
            .getLogger("net.richarddawkins.watchmaker.geom.Boxes");

    @Override
    public int getBoxCount() {
        return boxCount;
    }

    @Override
    public Rect getMidBox() {
        return midBox;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        if (cols > 0) {
            this.cols = cols;
            update();
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows > 0) {
            this.rows = rows;
            update();
        }
    }

    public int cols = 0;
    public int rows = 0;
    public int boxCount;
    public Rect midBox;

    public void update() {
        boxCount = boxes.size();
        rows = boxCount / cols + (boxCount % cols == 0 ? 0 : 1);
        
        if (boxCount >= rows * cols / 2) {
            midBox = boxes.elementAt(boxCount / 2);
        } else {
            midBox = null;
        }

    }

    public GridBoxManager(int cols) {
        this.cols = cols;
    }
    
    
    /**
     * Construct a cols * rows array of boxes.
     * 
     * @param cols
     *            the number of columns in the box array.
     * @param rows
     *            the number of rows in the box array.
     */
    public GridBoxManager(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        for (int i = 0; i < cols * rows; i++) {
            boxes.add(new Rect());
        }
        this.boxCount = cols * rows;
        this.midBox = boxes.elementAt(boxCount / 2);
    }

    public GridBoxManager() {
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.richarddawkins.watchmaker.geom.BoxManager#getBoxSize(net.
     * richarddawkins.watchmaker.geom.Dim)
     */
    @Override
    public Dim getBoxSize(Rect box, Dim dimension) {
        return new Dim(dimension.width / cols, dimension.height / rows);
    }

    @Override
    public Vector<Rect> getBoxes(Dim dimension) {
        int boxwidth = (int) dimension.width / cols;
        int boxheight = (int) dimension.height / rows;
        Iterator<Rect> rects = boxes.iterator();
        rowloop: for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                if (!rects.hasNext()) {
                    break rowloop;
                }
                int x = i * boxwidth;
                int y = j * boxheight;

                Rect rect = rects.next();
                rect.left = x;
                rect.right = x + boxwidth;
                rect.top = y;
                rect.bottom = y + boxheight;
                logger.fine(
                        "GetBoxes " + (i + j * cols) + ": " + rect.toString());
            }
        }
        return boxes;
    }

    @Override
    public Point getMidPoint(Dim dimension, Rect box) {
        int boxNo = boxes.indexOf(box);
        int col = boxNo % cols;
        int row = boxNo / cols;
        int boxwidth = dimension.width / cols;
        int boxheight = dimension.height / rows;
        int halfboxwidth = boxwidth / 2;
        int halfboxheight = boxheight / 2;
        return new Point(boxwidth * col + halfboxwidth,
                boxheight * row + halfboxheight);

    }

    /*
     * (non-Javadoc)
     * 
     * @see net.richarddawkins.watchmaker.geom.BoxManager#getMidPoints(net.
     * richarddawkins.watchmaker.geom.Dim)
     */
    @Override
    public Vector<Point> getMidPoints(Dim dimension) {

        int boxwidth = dimension.width / cols;
        int boxheight = dimension.height / rows;
        int halfboxwidth = boxwidth / 2;
        int halfboxheight = boxheight / 2;

        Vector<Point> midPoints = new Vector<Point>();
        for (int j = 0; j < rows; j++)
            for (int i = 0; i < cols; i++) {
                int x = i * boxwidth + halfboxwidth;
                int y = j * boxheight + halfboxheight;
                midPoints.add(new Point(x, y));
            }
        return midPoints;
    }

    @Override
    public void addBox(Rect box) {
        boxes.add(box);
        update();
    }

    @Override
    public void addBox(Rect box, Dim dim) {
        this.addBox(box);
    }

    @Override
    public Rect getBox(int boxNo) {
        // Add a row of boxes to make sure there's enough.
        if (boxNo >= boxCount) {
            for (int i = 0; i < cols; i++) {
                boxes.add(new Rect());
            }
            rows++;
            update();
        }

        Rect rect = super.getBox(boxNo);
        return rect;
    }

    @Override
    public Rect firstElement() {
        
        return boxes.firstElement();
    }

}
