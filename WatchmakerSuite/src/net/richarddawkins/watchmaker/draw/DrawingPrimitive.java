package net.richarddawkins.watchmaker.draw;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface DrawingPrimitive {
	public Rectangle getBounds(QuickDrawState qds);
	public void execute(Graphics2D g, QuickDrawState qds);
}
