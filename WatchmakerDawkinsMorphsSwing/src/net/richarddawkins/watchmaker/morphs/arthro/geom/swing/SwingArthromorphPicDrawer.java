package net.richarddawkins.watchmaker.morphs.arthro.geom.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthroLin;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthromorphDrawingPreferences;
import net.richarddawkins.watchmaker.morphs.mono.geom.Lin;
import net.richarddawkins.watchmaker.morphs.swing.SwingPicDrawer;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.swing.SwingColor;

public class SwingArthromorphPicDrawer extends SwingPicDrawer {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.arthro.geom.swing.SwingArthromorphPicDrawer");

	public SwingArthromorphPicDrawer() {
		drawingPreferences = new ArthromorphDrawingPreferences();
	}

	@Override
	protected void limb(Graphics2D g2, Phenotype arthroPic, Lin arthroLin) {
		ArthroLin lin = (ArthroLin) arthroLin;
		logger.info(lin.toString());
		
		Color drawColor = SwingColor.toColor(lin.color);
		Color fillColor = Color.GREEN;
		g2.setStroke(new BasicStroke(lin.thickness));
		
		switch (lin.arthroLimbType) {
		case Oval:
			g2.setColor(fillColor);
			g2.fillOval(lin.startPt.h, lin.startPt.v, lin.endPt.h, lin.endPt.v);
			g2.setColor(drawColor);
			g2.drawOval(lin.startPt.h, lin.startPt.v, lin.endPt.h, lin.endPt.v);
			break;
		case LineSegment:
			g2.setColor(drawColor);
			g2.drawLine(lin.startPt.h, lin.startPt.v, lin.endPt.h, lin.endPt.v);
			break;
		default:
		}
	}
}
