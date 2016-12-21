package net.richarddawkins.watchmaker.draw;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class FrameOval implements DrawingPrimitive {

	private int x;
	private int y;
	private int width;
	private int height;
	
	public FrameOval(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
			
	}
	
	public Rectangle getBounds(QuickDrawState qds) {
		return new Rectangle(x, y, width, height);		
	}
	
	@Override
	public void execute(Graphics2D g, QuickDrawState qds) {
			g.drawOval(x, y, width, height);
	}

}
