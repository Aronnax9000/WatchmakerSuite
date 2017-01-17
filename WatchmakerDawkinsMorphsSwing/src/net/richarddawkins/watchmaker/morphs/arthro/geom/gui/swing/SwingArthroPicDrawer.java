package net.richarddawkins.watchmaker.morphs.arthro.geom.gui.swing;

import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.geom.Lin;
import net.richarddawkins.watchmaker.geom.Pic;
import net.richarddawkins.watchmaker.geom.swing.SwingPicDrawer;
import net.richarddawkins.watchmaker.morphs.arthro.geom.ArthroLin;

public class SwingArthroPicDrawer extends SwingPicDrawer {

    
    @Override
    protected void limb(Graphics2D g2, Pic pic, Lin line) {
        ArthroLin arthroLin = (ArthroLin) line;
        // To do.
    }


}
