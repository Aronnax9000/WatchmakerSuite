package net.richarddawkins.watchmaker.morphs.arthro.geom.swing;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthroLin;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthromorphDrawingPreferences;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthromorphPic;
import net.richarddawkins.watchmaker.morphs.mono.geom.Lin;
import net.richarddawkins.watchmaker.morphs.swing.SwingPicDrawer;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.swing.SwingColor;

public class SwingArthromorphPicDrawer extends SwingPicDrawer {

	public SwingArthromorphPicDrawer() {
		drawingPreferences = new ArthromorphDrawingPreferences();
	}

	@Override
	protected void limb(Graphics2D g2, Phenotype arthroPic, Lin arthroLin) {

		ArthromorphPic pic = (ArthromorphPic) arthroPic;
		ArthroLin lin = (ArthroLin) arthroLin;
		g2.setColor(SwingColor.rgbColorPalette[lin.color]);
		g2.setStroke(new BasicStroke(lin.thickness));
		switch (lin.arthroLimbType) {
		case Oval:
			g2.drawOval(lin.startPt.h, lin.startPt.v, lin.endPt.h, lin.endPt.v);
			break;
		case LineSegment:
			g2.drawLine(lin.startPt.h, lin.startPt.v, lin.endPt.h, lin.endPt.v);
			break;
		default:
		}
	}
}
