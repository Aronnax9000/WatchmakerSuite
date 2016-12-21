package net.richarddawkins.watchmaker.draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SetColor implements DrawingPrimitive {

	private Color color;
	
	public SetColor(Color color) {
		this.color = color;
	}
	public Rectangle getBounds(QuickDrawState qds) { return null; }
	
	@Override
	public void execute(Graphics2D g, QuickDrawState qds) {
		g.setColor(color);
	}

}
