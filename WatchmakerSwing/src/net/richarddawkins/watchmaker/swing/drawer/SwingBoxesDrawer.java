package net.richarddawkins.watchmaker.swing.drawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.swing.SwingColor;

public class SwingBoxesDrawer implements BoxesDrawer {

    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.drawer.SwingBoxesDrawer");

	/**
	 * Draw boxes on a Graphics2D context.
	 * @param graphicsContext the Graphics2D on which drawing is to take place.
	 * @param dimension the size of the overall grid of boxes.
	 * @param boxes the BoxManager
	 * @param midBoxOnly whether only to draw the midBox.
	 * @param backgroundColors a list of background colors for the box list (a morph's phenotype may have a background color.)
	 */
	public void draw(Object graphicsContext, 
			Dim dimension, 
			BoxManager boxes, 
			boolean midBoxOnly,
			Vector<Integer> backgroundColors) {
		Graphics2D g2 = (Graphics2D) graphicsContext;
		Stroke saveStroke = g2.getStroke();
		Iterator<Integer> backgroundColorIterator = backgroundColors.iterator();

		Vector<Rect> boxRects = boxes.getBoxes(dimension);
		logger.fine("Got " + boxRects.size() + " boxes with dimensions " + dimension.toString());
		Rect midBox = boxes.getMidBox();
		boolean accentuateMidBox = boxes.isAccentuateMidBox();
		for(Rect r: boxRects) {
			// Middle box has a thicker line
			if(r == midBox && accentuateMidBox) {
				g2.setStroke(new BasicStroke(4.0f));
			} else {
				g2.setStroke(new BasicStroke(2.0f));
			}
			int backgroundColor = backgroundColorIterator.next();
			if(! midBoxOnly || (r == midBox && accentuateMidBox)) {
				if(backgroundColor != -1) {
					g2.setColor(SwingColor.rgbColorPalette[backgroundColor]);
					g2.fillRect(r.left, r.top, r.getWidth(), r.getHeight());
				}
				g2.setColor(Color.BLACK);
				g2.drawRect(r.left, r.top, r.getWidth(), r.getHeight());
			}
			g2.setStroke(saveStroke);
		}
		logger.fine("Finished Drawing Boxes");
		
	}
}
