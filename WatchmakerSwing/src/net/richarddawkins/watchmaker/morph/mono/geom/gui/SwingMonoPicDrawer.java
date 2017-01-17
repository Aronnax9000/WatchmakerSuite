package net.richarddawkins.watchmaker.morph.mono.geom.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Pic;
import net.richarddawkins.watchmaker.morph.biomorph.geom.QuickDrawColor;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SwingPicDrawer;
import net.richarddawkins.watchmaker.morph.mono.geom.MonoLin;

public class SwingMonoPicDrawer extends SwingPicDrawer {
    public void limb(Graphics2D g2, Pic pic, Lin line) {   
        MonoLin monoLin = (MonoLin) line;
        g2.setColor(new Color(QuickDrawColor.BLACK));
        g2.setStroke(new BasicStroke(monoLin.thickness));

        g2.drawLine(line.startPt.h, line.startPt.v, line.endPt.h, line.endPt.v);
    }
}
