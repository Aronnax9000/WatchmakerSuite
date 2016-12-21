package net.richarddawkins.watchmaker.draw;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class LineTo extends SimpleDrawingPrimitive implements DrawingPrimitive {

	private Point point = new Point();
	public LineTo(int x, int y) {
		point.x = x;
		point.y = y;
	}
	
	@Override
	public Rectangle getBounds(QuickDrawState qds) {
		Point penPos = qds.getPenPos();
		int minX = Math.min(penPos.x, point.x);
		int minY = Math.min(penPos.y, point.y);
		int maxX = Math.max(penPos.x, point.x);
		int maxY = Math.max(penPos.y, point.y);
		qds.setPenPos(point);
		return new Rectangle(
				minX, minY, maxX - minX, maxY - minY);
		
	}
	
	
	@Override
	public void execute(Graphics2D g, QuickDrawState qds) {
		Point penPos = qds.getPenPos();
		g.drawLine(penPos.x, penPos.y, point.x, point.y);
		qds.setPenPos(point);
	}
}
