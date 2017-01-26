package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.geom.BoxManager;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphVector;
import net.richarddawkins.watchmaker.morph.draw.MorphDrawer;
import net.richarddawkins.watchmaker.phenotype.PhenotypeDrawer;
import net.richarddawkins.watchmaker.swing.SwingGeom;
import net.richarddawkins.watchmaker.swing.drawer.SwingMorphDrawer;

public abstract class SwingBoxedMorphViewPanel extends SwingMorphViewPanel implements PropertyChangeListener {
	protected boolean showBoxes = true;
	
	private static final long serialVersionUID = -224189863008500654L;
	protected BoxedMorphVector boxedMorphVector = new BoxedMorphVector();
	protected BoxManager boxes;
	public BoxManager getBoxes() {
		return boxes;
	}
	public BoxedMorphVector getBoxedMorphVector() {
		return boxedMorphVector;
	}
	public void setBoxedMorphVector(BoxedMorphVector boxedMorphVector) {
		this.boxedMorphVector = boxedMorphVector;
	}

	public SwingBoxedMorphViewPanel(AppData appData) {
		super(appData);
	}

    public void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        Dim size = SwingGeom.toWatchmakerDim(getSize());
        updateModel(size);
        if(showBoxes) 
        	appData.getBoxesDrawer().draw(g2, size, boxes);
        for(BoxedMorph boxedMorph: boxedMorphVector.getBoxedMorphs()) {
        	morphDrawer.setSize(boxes.getBoxSize(size));
        	morphDrawer.draw(boxedMorph, g2, size);
        }
    }
    abstract protected void updateModel(Dim size);
}