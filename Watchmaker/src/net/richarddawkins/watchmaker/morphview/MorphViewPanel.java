package net.richarddawkins.watchmaker.morphview;

import java.awt.Cursor;
import java.util.Vector;

import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;

public interface MorphViewPanel   {

    void setCursor(Cursor cursor);

    Cursor getCursor();

    Vector<Morph> getMorphs();

    BoxedMorphCollection getBoxedMorphCollection();

    void paintMorphViewPanel(Object graphicsContext, Dim size);

    void setBoxedMorphCollection(BoxedMorphCollection boxedMorphVector);

    void updateCursor();

    void setBoxManager(BoxManager boxManager);

    Morph getMorphOfTheHour();



    Dim getDim();

    void repaint();

}
