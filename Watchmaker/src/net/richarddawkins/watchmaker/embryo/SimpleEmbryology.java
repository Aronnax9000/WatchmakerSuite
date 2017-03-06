package net.richarddawkins.watchmaker.embryo;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.morph.Morph;

public class SimpleEmbryology implements Embryology {
	
	protected EmbryologyPreferences prefs;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.embryo.SimpleEmbryology");

	@Override
	public void develop(Morph morph) {
		morph.getPhenotype().zero();
		// Invalidate the morph's image.
		morph.setImage(null);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("genome")) {
			Morph morph = (Morph)evt.getSource();
			develop(morph);
			PropertyChangeEvent event = new PropertyChangeEvent(morph, "phenotype", null, morph.getPhenotype());
			morph.firePropertyChange(event);
		}
	}

	@Override
	public EmbryologyPreferences getEmbryologyPreferences() {
		return prefs;
	}
}
