package net.richarddawkins.watchmaker.swing.triangle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.FreeBoxManager;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphView;

public class SwingTriangleMorphView extends SwingMorphView {
	protected Point[] trianglePoints = new Point[] { new Point(234, 41), new Point(134, 250), new Point(333, 250) };

	
	private static final long serialVersionUID = -5445629768562940527L;

	/**
	 * <pre>
	 * 		a.h := round(234 * ScreenWidth / 512);
	 * 		a.v := round(51 * ScreenHeight / 342);
	 * 		b.h := round(134 * ScreenWidth / 512);
	 * 		b.v := round(250 * ScreenHeight / 342);
	 * 		c.h := round(333 * ScreenWidth / 512);
	 * 		c.v := round(250 * ScreenHeight / 342);
	 * </pre>
	 * 
	 * @param appData
	 */
	public SwingTriangleMorphView(AppData appData) {
		super(appData);
		setName("Triangle");
		

		boxedMorphVector.setBoxes(new FreeBoxManager());
		Dim dim = new Dim(512, 342);
		Morph morph;
		MorphConfig config = appData.getMorphConfig();
		BoxManager boxes = boxedMorphVector.getBoxes();

		for (int i = 0; i < 3; i++) {
			morph = config.newMorph(i + 1);
			Rect margin = morph.getPhenotype().getMargin();
			Point morphMidPoint = margin.getMidPoint();
			Point displacement = trianglePoints[i].subtract(morphMidPoint);
			Rect newRect = new Rect(displacement.h, displacement.v, margin.getWidth(), margin.getHeight());
				
			boxes.addBox(newRect, dim);
			BoxedMorph boxedMorph = new BoxedMorph(boxes, morph, i);
			boxedMorphVector.add(boxedMorph);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Vector<Point> points = new Vector<Point>();
		BoxManager boxes = boxedMorphVector.getBoxes();
		for (int boxNo = 0; boxNo < 3; boxNo++) {
			Dim dim = SwingGeom.toWatchmakerDim(getSize());
			Point midPoint = boxes.getMidPoint(dim, boxNo);
			points.add(midPoint);
		}
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		g2.drawLine(points.elementAt(0).h, points.elementAt(0).v, points.elementAt(1).h, points.elementAt(1).v);
		g2.drawLine(points.elementAt(1).h, points.elementAt(1).v, points.elementAt(2).h, points.elementAt(2).v);
		g2.drawLine(points.elementAt(2).h, points.elementAt(2).v, points.elementAt(0).h, points.elementAt(0).v);
		super.paintComponent(g);
	}



    @Override
    public Morph getMorphOfTheHour() {
    	return boxedMorphVector
    			.getBoxedMorph(boxedMorphVector.getBoxedMorphs().size() - 1)
    			.getMorph();
    }

	@Override
	public void seed(Morph morph) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void boxClicked(Point point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processMouseMotion(Point myPt, Dim size) {
		// TODO Auto-generated method stub
		
	}
}
