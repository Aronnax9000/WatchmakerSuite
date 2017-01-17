package net.richarddawkins.watchmaker.gui.engineer;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import net.richarddawkins.watchmaker.drawer.BoxedMorph;
import net.richarddawkins.watchmaker.drawer.Boxes;
import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.breed.BoxyMorphViewPanel;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

public class EngineeringPanel extends BoxyMorphViewPanel implements PropertyChangeListener {
	EngineeringWatchmakerPanel engineeringWatchmakerPanel;
	EngineeringPanelMouseAdapter mouseAdapter;
	public EngineeringPanel(EngineeringWatchmakerPanel engineeringWatchmakerPanel) {
	    super(engineeringWatchmakerPanel.getMorphConfig());
		setCursor(WatchmakerCursors.hypodermic);
		this.engineeringWatchmakerPanel = engineeringWatchmakerPanel;
		boxesDrawer = new Boxes(1,1);
		mouseAdapter = new EngineeringPanelMouseAdapter(this);
	}

	private static final long serialVersionUID = -5519820942516604540L;

	public void seed(Morph morph) {
		if(! boxedMorphVector.isEmpty()) {
			for(BoxedMorph boxedMorph: boxedMorphVector.getBoxedMorphs()) {
				boxedMorph.getMorph().getGenome().removePropertyChangeListener(this);
			}
			boxedMorphVector.removeAllElements();
		}
		morph.getGenome().addPropertyChangeListener(this);
		boxedMorphVector.add(new BoxedMorph(morph, 0));
		GeneBoxStrip geneBoxStrip = (GeneBoxStrip)this.engineeringWatchmakerPanel.getPageStartPanel();
		geneBoxStrip.setGenome(morph.getGenome());
	}

	@Override
	public void updateModel(Dimension size) {
		this.getBoxedMorphVector().getBoxedMorph(0).setPosition(boxesDrawer.getMidPoint(size, 0));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		repaint();
		
	}

}
