package net.richarddawkins.watchmaker.swing.drawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Rect;

public class SwingBoxesDrawer implements BoxesDrawer {

    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.drawer.SwingBoxesDrawer");

	/**
	 * Draw boxes on a Graphics2D context.
	 * @param g the Graphics2D on which drawing is to take place.
	 * @param dimension the size of the overall grid of boxes.
	 */
	public void draw(Object graphicsContext, Dim dimension, BoxManager boxes) {
		Graphics2D g2 = (Graphics2D) graphicsContext;
		g2.setColor(Color.BLACK);
		Stroke saveStroke = g2.getStroke();
		int boxIndex = 0;
		Vector<Rect> boxRects = boxes.getBoxes(dimension);
		logger.fine("Got " + boxRects.size() + " boxes with dimensions " + dimension.toString());
		int midBox = boxes.getMidBox();
		for(Rect r: boxRects) {
			
			// Middle box has a thicker line
			if(boxIndex == midBox) {
				g2.setStroke(new BasicStroke(4.0f));
			} else {
				g2.setStroke(new BasicStroke(2.0f));
			}
			g2.drawRect(r.left, r.top, r.getWidth(), r.getHeight());
			g2.setStroke(saveStroke);
			boxIndex++;
		}
		logger.fine("Finished Drawing Boxes");
		
	}
}
