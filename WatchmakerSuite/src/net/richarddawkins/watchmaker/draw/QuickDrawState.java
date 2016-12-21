package net.richarddawkins.watchmaker.draw;

import java.awt.Dimension;
import java.awt.Point;

public class QuickDrawState {
	
    private Point penPos = new Point(0,0);
    private float penSize = 1.0f;

	public void setPenPos(Point penPos) { this.penPos = penPos; }
    public Point getPenPos() { return penPos; }	Dimension preferredSize = new Dimension(250,200);


	public void setPenSize(float pensize) {
		this.penSize = pensize;
		
	}
	public float getPenSize() {
		return penSize;
	}

	  
}
