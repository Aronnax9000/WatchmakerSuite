package net.richarddawkins.watchmaker.morphview;

import java.beans.PropertyChangeListener;

import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.geom.BoxManager;

public interface ScaleSlider extends MorphViewWidget, ChangeListener, PropertyChangeListener {

    void setBoxManager(BoxManager boxManager);

}
