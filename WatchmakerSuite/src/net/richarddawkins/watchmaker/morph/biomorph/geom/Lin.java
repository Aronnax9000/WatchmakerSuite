package net.richarddawkins.watchmaker.morph.biomorph.geom;

import java.awt.Color;


public class Lin {
	public Point startPt;
	public Point endPt;
	public Color color = Color.BLACK;
	public int thickness = 1; // 1..8
	
	public Lin(Point startPt, Point endPt, int thickness, Color color) {
	    this.startPt = startPt;
	    this.endPt = endPt;
	    this.color = color;
	    this.thickness = thickness;
	}
}
