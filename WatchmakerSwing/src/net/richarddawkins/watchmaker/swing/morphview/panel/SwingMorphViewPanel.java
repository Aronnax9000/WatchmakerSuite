package net.richarddawkins.watchmaker.swing.morphview.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.BoxesDrawer;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphVector;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.morphview.MorphViewPanel;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer;

public abstract class SwingMorphViewPanel extends JPanel implements MorphViewPanel, PropertyChangeListener {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.morphview.panel.SwingMorphViewPanel");

    /**
	 * 
	 */
	private static final long serialVersionUID = -6896360202157783971L;
	protected AppData appData;
	protected BoxedMorphVector boxedMorphVector = new BoxedMorphVector();
	protected BoxManager boxes;
	protected boolean showBoxes = true;

    protected MorphDrawer morphDrawer;
    public SwingMorphViewPanel(AppData appData) {
		super();
		this.appData = appData;
		PhenotypeDrawer phenotypeDrawer = appData.getPhenotypeDrawer();
		morphDrawer = new SwingMorphDrawer(phenotypeDrawer);
		phenotypeDrawer.getDrawingPreferences().addPropertyChangeListener(this);
	}

	public SwingMorphViewPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public SwingMorphViewPanel(LayoutManager layout) {
		super(layout);
	}

	public SwingMorphViewPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public AppData getAppData() {
		return appData;
	}

	public void propertyChange (PropertyChangeEvent event) {
    	String propertyName = event.getPropertyName();
    	switch(propertyName) {
    	case "showBoundingBoxes":
    	case "scale":
    	case "phenotype":
    		logger.info("SwingMorphViewPanel propertyChange:" + propertyName);
    		for(Morph morph: getMorphs()) {
    			morph.setImage(null);
    		}
    		repaint();
    		break;
    	default:
    	}
    }

	public void setAppData(AppData appData) {
		this.appData = appData;
	}
	public BoxedMorphVector getBoxedMorphVector() {
		return boxedMorphVector;
	}

	public BoxManager getBoxes() {
		return boxes;
	}

	@Override
	public Vector<Morph> getMorphs() {
		return boxedMorphVector.getMorphs();
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		Dim size = SwingGeom.toWatchmakerDim(getSize());
		updateModel(size);
		Vector<Integer> backgroundColors = new Vector<Integer>();
		for(int i = 0; i < this.boxes.getBoxCount(); i++) {
			BoxedMorph boxedMorph = boxedMorphVector.getBoxedMorph(i);
			if(boxedMorph != null) {
				backgroundColors.add(boxedMorph.getMorph().getPhenotype().getBackgroundColor());
			} else {
				backgroundColors.add(-1);
			}
		}
		if (showBoxes) {
			BoxesDrawer boxesDrawer = appData.getBoxesDrawer();
			boxesDrawer.draw(
					g2, 
					size, 
					boxes, 
					boxedMorphVector.getBoxedMorphs().size() == 1,
					backgroundColors
					);
		}
		for (BoxedMorph boxedMorph : boxedMorphVector.getBoxedMorphs()) {
			morphDrawer.setSize(boxes.getBoxSize(size));
			morphDrawer.draw(boxedMorph, g2, size);
		}
	}

	public void setBoxedMorphVector(BoxedMorphVector boxedMorphVector) {
		this.boxedMorphVector = boxedMorphVector;
	}
	protected abstract void updateModel(Dim size);


}