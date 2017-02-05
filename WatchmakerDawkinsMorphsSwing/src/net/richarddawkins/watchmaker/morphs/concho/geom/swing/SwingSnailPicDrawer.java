package net.richarddawkins.watchmaker.morphs.concho.geom.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morphs.bio.geom.Lin;
import net.richarddawkins.watchmaker.morphs.bio.geom.QuickDrawColor;
import net.richarddawkins.watchmaker.morphs.concho.geom.SnailPic;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoLin;
import net.richarddawkins.watchmaker.morphs.mono.geom.swing.SwingMonoPicDrawer;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.phenotype.SimpleDrawingPreferences;

public class SwingSnailPicDrawer extends SwingMonoPicDrawer {

	public SwingSnailPicDrawer() {
		drawingPreferences = new SimpleDrawingPreferences();
	}
	@Override
	public void limb(Graphics2D g2, Phenotype snailPic, Lin line) {   
        MonoLin monoLin = (MonoLin) line;
        SnailPic pic = (SnailPic) snailPic;
        g2.setColor(new Color(QuickDrawColor.BLACK));
        g2.setStroke(new BasicStroke(monoLin.thickness));
        // TODO hic sunt draconis
        if(pic.isSideView()) {
        	g2.drawOval(line.startPt.h, line.startPt.v, line.endPt.h, line.endPt.v);
        } else {
        	g2.drawLine(line.startPt.h, line.startPt.v, line.endPt.h, line.endPt.v);
        }
    }
}
