package net.richarddawkins.watchmaker.morphview;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;

public interface MorphViewPanel extends PropertyChangeListener  {

    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);
    
    void setCursor(Object cursor);

    Object getCursor();

    Vector<Morph> getMorphs();

    BoxedMorphCollection getBoxedMorphCollection();


    void setBoxedMorphCollection(BoxedMorphCollection boxedMorphVector);

    void updateCursor();

    Morph getMorphOfTheHour();

    MorphView getMorphView();


    Dim getDim();

    void repaint();
    void paintMorphViewPanel(Object graphicsContext, Dim size);
    boolean isAutoScale();
    void setAutoScale(boolean autoScale);
    void gainFocus();
    void loseFocus();
    Object getPanel();
    String getName();
    void setName(String name);

}
