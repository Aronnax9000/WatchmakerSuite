package net.richarddawkins.watchmaker.morphview;

import java.awt.Cursor;
import java.beans.PropertyChangeListener;
import java.util.Vector;

import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;

public interface MorphViewPanel extends PropertyChangeListener  {

    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
    
    void setCursor(Cursor cursor);

    Cursor getCursor();

    Vector<Morph> getMorphs();

    BoxedMorphCollection getBoxedMorphCollection();

    void paintMorphViewPanel(Object graphicsContext, Dim size);

    void setBoxedMorphCollection(BoxedMorphCollection boxedMorphVector);

    void updateCursor();

    Morph getMorphOfTheHour();

    MorphView getMorphView();


    Dim getDim();

    void repaint();

}
