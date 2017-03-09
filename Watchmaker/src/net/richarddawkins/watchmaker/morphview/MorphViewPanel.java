package net.richarddawkins.watchmaker.morphview;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;

public interface MorphViewPanel extends PropertyChangeListener  {


    void setCursor(Object cursor);

    Object getCursor();


    void updateCursor();


    Dim getDim();

    Object getPanel();
    void removePropertyChangeListener(PropertyChangeListener listener);
    void setBoxedMorphCollection(BoxedMorphCollection newValue);
    void setShowBoundingBoxes(boolean showBoundingBoxes);
    void setSelectedBox(Rect newValue);
    void setName(String name);
    void setIncludeChildrenInAutoScale(boolean includeChildrenInAutoScale);
    void processMousePressed(Point watchmakerPoint, Dim watchmakerDim);
    void setSpecial(Rect newValue);
    void processMouseDragged(Point watchmakerPoint, Dim watchmakerDim);
    void processMouseClicked(Point point, Dim size);
    boolean isShowBoundingBoxes();
    Rect getSpecial();
    String getName();
    Vector<Morph> getMorphs();
    Morph getMorphOfTheHour();
    boolean getIncludeChildrenInAutoScale();
    BoxedMorphCollection getBoxedMorphCollection();
    void clearMorphImages();
    void autoScaleBasedOnMorphs(Rect special);
    void addPropertyChangeListener(PropertyChangeListener listener);
    MorphView getMorphView();
    void setAutoScale(boolean autoScale);
    boolean isAutoScale();
    void loseFocus();
    void gainFocus();
    void repaint();

    void paintMorphViewPanel(Object graphicsContext, Dim size);

}
