package net.richarddawkins.watchmaker.morphs.mono.geom.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morphs.mono.geom.Lin;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoLin;
import net.richarddawkins.watchmaker.morphs.mono.geom.QuickDrawColor;
import net.richarddawkins.watchmaker.morphs.swing.SwingPicDrawer;
import net.richarddawkins.watchmaker.phenotype.Phenotype;
import net.richarddawkins.watchmaker.phenotype.SimpleDrawingPreferences;

public class SwingMonoPicDrawer extends SwingPicDrawer {
    
	public SwingMonoPicDrawer() {
		drawingPreferences = new SimpleDrawingPreferences();
	}
	
	public void limb(Graphics2D g2, Phenotype pic, Lin line) {   
        MonoLin monoLin = (MonoLin) line;
        g2.setColor(new Color(QuickDrawColor.BLACK));
        g2.setStroke(new BasicStroke(monoLin.thickness));

        g2.drawLine(line.startPt.h, line.startPt.v, line.endPt.h, line.endPt.v);
    }


    
}
