package net.richarddawkins.watchmaker.embryo;

import java.beans.PropertyChangeEvent;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class SimpleEmbryology implements Embryology {
    private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.embryo.SimpleEmbryology");

	@Override
	public void develop(Genome genome, Phenotype phenotype) {
		phenotype.zero();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("genome"));
		Morph morph = (Morph)evt.getSource();
		develop((Genome)evt.getNewValue(), morph.getPic());
		morph.firePropertyChange(new PropertyChangeEvent(morph, "phenotype", null, morph.getPic()));
	}
}
