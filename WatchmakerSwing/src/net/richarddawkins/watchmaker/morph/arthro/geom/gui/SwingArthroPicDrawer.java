package net.richarddawkins.watchmaker.morph.arthro.geom.gui;

import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morph.arthro.geom.ArthroLin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Lin;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Pic;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SwingPicDrawer;

public class SwingArthroPicDrawer extends SwingPicDrawer {

    
    @Override
    protected void limb(Graphics2D g2, Pic pic, Lin line) {
        ArthroLin arthroLin = (ArthroLin) line;
        // To do.
    }
}
