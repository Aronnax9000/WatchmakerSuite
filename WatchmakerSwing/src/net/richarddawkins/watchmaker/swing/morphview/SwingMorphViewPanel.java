package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer;

public class SwingMorphViewPanel extends JPanel implements MorphViewPanel, PropertyChangeListener {

    protected AppData appData;
	protected MorphDrawer morphDrawer;


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
		PhenotypeDrawer phenotypeDrawer = appData.getPhenotypeDrawer();
		morphDrawer = new SwingMorphDrawer(phenotypeDrawer);
		phenotypeDrawer.getDrawingPreferences().addPropertyChangeListener(this);
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