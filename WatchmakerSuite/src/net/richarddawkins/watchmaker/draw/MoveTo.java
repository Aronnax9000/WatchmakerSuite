package net.richarddawkins.watchmaker.draw;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class MoveTo implements DrawingPrimitive {

	Point point = new Point();

	public MoveTo(int x, int y) {
		point.x = x;
		point.y = y;
	}
	
	public Rectangle getBounds(QuickDrawState qds) {
		qds.setPenPos(point);
		return null;
	}
	
	@Override
	public void execute(Graphics2D g, QuickDrawState qds) {
		qds.getPenPos().setLocation(new Point(point.x, point.y));
	}
		
	

}
