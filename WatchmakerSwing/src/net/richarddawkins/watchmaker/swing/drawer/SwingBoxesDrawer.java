package net.richarddawkins.watchmaker.swing.drawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;

import net.richarddawkins.watchmaker.geom.Boxes;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;

public class SwingBoxesDrawer implements BoxesDrawer {
	/**
	 * Draw boxes on a Graphics2D context.
	 * @param g the Graphics2D on which drawing is to take place.
	 * @param dimension the size of the overall grid of boxes.
	 */
	public void draw(Object graphicsContext, Dimension dimension, Boxes boxes) {
		Graphics2D g = (Graphics2D) graphicsContext;
		g.setColor(Color.BLACK);
		int boxIndex = 0;
		Stroke saveStroke = g.getStroke();
		for(Rectangle r: boxes.getBoxes(dimension)) {
			
			// Middle box has a thicker line
			if(boxIndex == boxes.midBox) {
				g.setStroke(new BasicStroke(2.0f));
			}
			
			g.drawRect(r.x, r.y, r.width, r.height);
			g.setStroke(saveStroke);
			boxIndex++;
		}
	}
}
