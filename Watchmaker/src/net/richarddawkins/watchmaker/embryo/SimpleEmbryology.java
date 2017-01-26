package net.richarddawkins.watchmaker.embryo;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.morph.Morph;

public class SimpleEmbryology implements Embryology {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.embryo.SimpleEmbryology");

	@Override
	public void develop(Morph morph) {
		morph.getPic().zero();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("genome")) {
			Morph morph = (Morph)evt.getSource();
			develop(morph);
			morph.firePropertyChange(new PropertyChangeEvent(morph, "phenotype", null, morph.getPic()));
		}
	}
}
