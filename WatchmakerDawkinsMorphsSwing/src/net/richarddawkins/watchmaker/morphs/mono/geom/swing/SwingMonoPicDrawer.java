package net.richarddawkins.watchmaker.morphs.mono.geom.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.geom.Lin;
import net.richarddawkins.watchmaker.geom.Pic;
import net.richarddawkins.watchmaker.morphs.bio.geom.QuickDrawColor;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoLin;
import net.richarddawkins.watchmaker.swing.geom.SwingPicDrawer;

public class SwingMonoPicDrawer extends SwingPicDrawer {
    public void limb(Graphics2D g2, Pic pic, Lin line) {   
        MonoLin monoLin = (MonoLin) line;
        g2.setColor(new Color(QuickDrawColor.BLACK));
        g2.setStroke(new BasicStroke(monoLin.thickness));

        g2.drawLine(line.startPt.h, line.startPt.v, line.endPt.h, line.endPt.v);
    }
}