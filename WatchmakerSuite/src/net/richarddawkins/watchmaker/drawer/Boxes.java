package net.richarddawkins.watchmaker.drawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.Vector;

import net.richarddawkins.watchmaker.morph.common.Morph;

public class Boxes  {

	protected int cols;
	protected int rows;
	protected Vector<BoxedMorph> boxedMorphs = new Vector<BoxedMorph>();
	
	public Morph getMorph(int boxNo) {
		for(BoxedMorph boxedMorph: boxedMorphs) 
			if(boxedMorph.boxNo == boxNo)
				return boxedMorph.morph;
		return null;
	}
	
	public Vector<BoxedMorph> getBoxedMorphs() {
		return boxedMorphs;
	}

	public void setBoxedMorphs(Vector<BoxedMorph> boxedMorphs) {
		this.boxedMorphs = boxedMorphs;
	}

	public Boxes(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
	}
	
	public int getBoxNoContainingPoint(Point p, Dimension d) {
		int boxIndex = 0;
		for(Rectangle box: getBoxes(d)) {
			if(box.contains(p)) {
				return boxIndex;
			}
			boxIndex++;
		}
		return -1;
	}
	
	public Vector<Rectangle> getBoxes(Dimension dimension)
	{
		Vector<Rectangle> boxes = new Vector<Rectangle>();
		int boxwidth = dimension.width / cols;
		int boxheight = dimension.height / rows;
		for(int j = 0; j < rows; j++)
			for(int i = 0; i < cols; i++)
			{
				int x = i * boxwidth;
				int y = j * boxheight;
				boxes.add(new Rectangle(x, y, boxwidth, boxheight));
			}
		return boxes;
	}
	
	public Vector<Point> getMidPoints(Dimension dimension) {
		
		int boxwidth = dimension.width / cols;
		int boxheight = dimension.height / rows;
		int halfboxwidth = boxwidth / 2;
		int halfboxheight = boxheight / 2;

		Vector<Point> midPoints = new Vector<Point>();
		for(int j = 0; j < rows; j++)
			for(int i = 0; i < cols; i++)
			{
				int x = i * boxwidth;
				int y = j * boxheight;
				midPoints.add(new Point(x + halfboxwidth, y + halfboxheight));
			}
		return midPoints;
	}
	

	public void draw(Graphics2D g, Dimension dimension) {
		
		g.setColor(Color.BLACK);
		int boxIndex = 0;
		Stroke saveStroke = g.getStroke();
		for(Rectangle r: getBoxes(dimension.getSize())) {
			if(boxIndex == rows * cols / 2) {
				g.setStroke(new BasicStroke(2.0f));
			}
			g.drawRect(r.x, r.y, r.width, r.height);
			g.setStroke(saveStroke);
			boxIndex++;
		}
		
				
	}

}
