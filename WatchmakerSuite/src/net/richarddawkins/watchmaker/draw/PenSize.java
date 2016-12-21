package net.richarddawkins.watchmaker.draw;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class PenSize implements DrawingPrimitive {

	float penSize;
	public PenSize(float pensize) {
		this.penSize = pensize;
	}
	
	@Override
	public Rectangle getBounds(QuickDrawState qds) {
        qds.setPenSize(penSize);
		return null;
	}

	@Override
	public void execute(Graphics2D g, QuickDrawState qds) {
		g.setStroke(new BasicStroke(penSize));

	}

}
