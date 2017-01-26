package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;

public class SwingMorphViewPanel extends JPanel implements MorphViewPanel, PropertyChangeListener {

    protected AppData appData;


    public void propertyChange (PropertyChangeEvent event) {
    	String propertyName = event.getPropertyName();
    	switch(propertyName) {
    	case "showBoundingBoxes":
    	case "scale":
    		repaint();
    	default:
    	}
    }
    /**
	 * 
	 */
	private static final long serialVersionUID = -6896360202157783971L;

	public SwingMorphViewPanel(AppData appData) {
		super();
		this.appData = appData;
		appData.getPhenotypeDrawer().getDrawingPreferences().addPropertyChangeListener(this);
	}

	public AppData getAppData() {
		return appData;
	}

	public void setAppData(AppData appData) {
		this.appData = appData;
	}

	public SwingMorphViewPanel(LayoutManager layout) {
		super(layout);
	}

	public SwingMorphViewPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public SwingMorphViewPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

}