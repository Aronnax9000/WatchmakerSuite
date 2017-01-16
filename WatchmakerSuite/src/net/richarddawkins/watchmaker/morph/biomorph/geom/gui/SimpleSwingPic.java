package net.richarddawkins.watchmaker.morph.biomorph.geom.gui;

import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.geom.SimplePic;

public abstract class SimpleSwingPic extends SimplePic {
    public SimpleSwingPic(Morph morph) {
        super(morph);
    }

    abstract public void drawPic(Graphics2D g2);

}
