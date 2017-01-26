package net.richarddawkins.watchmaker.swing.engineer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Boxes;
import net.richarddawkins.watchmaker.geom.Dim;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
import net.richarddawkins.watchmaker.swing.morphview.SwingBoxedMorphViewPanel;

public class SwingEngineeringBoxedMorphViewPanel extends SwingBoxedMorphViewPanel implements PropertyChangeListener {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.swing.engineer.SwingEngineeringBoxedMorphViewPanel");

	SwingEngineeringMorphView engineeringWatchmakerPanel;
	MouseAdapter mouseAdapter;

	public SwingEngineeringBoxedMorphViewPanel(SwingEngineeringMorphView engineeringWatchmakerPanel) {
		super(engineeringWatchmakerPanel.getAppData());
		setCursor(WatchmakerCursors.hypodermic);
		this.engineeringWatchmakerPanel = engineeringWatchmakerPanel;
		boxes = new Boxes(1, 1);
		mouseAdapter = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				// Raise message about hypodermic.
			}
		};
	}

	private static final long serialVersionUID = -5519820942516604540L;

	public void seed(Morph morph) {
		if (!boxedMorphVector.isEmpty()) {
			for (BoxedMorph boxedMorph : boxedMorphVector.getBoxedMorphs()) {
				boxedMorph.getMorph().removePropertyChangeListener(this);
			}
			boxedMorphVector.removeAllElements();
		}
		morph.addPropertyChangeListener(this);
		boxedMorphVector.add(new BoxedMorph(boxes, morph, 0));
		GeneBoxStrip geneBoxStrip = (GeneBoxStrip) this.engineeringWatchmakerPanel.getUpperStrip();
		geneBoxStrip.setGenome(morph.getGenome());
	}

	@Override
	public void updateModel(Dim size) {
		this.getBoxedMorphVector().getBoxedMorph(0).setPosition(boxes.getMidPoint(size, 0));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getPropertyName().equals("phenotype")) {
			getBoxedMorphVector().getBoxedMorph(0).getMorph().setImage(null);
			repaint();
		}
	}

}
