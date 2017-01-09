package net.richarddawkins.watchmaker.morph.simple;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.Mutagen;

public class OvalMorphMutagen implements Mutagen {

	protected OvalMorphConfig config;
	@Override
	public void setMorphConfig(MorphConfig config) {
		this.config = (OvalMorphConfig) config;

	}

	@Override
	public MorphConfig getMorphConfig() {
		
		return config;
	}

	@Override
	public boolean mutate(Genome genome) {
		// TODO Auto-generated method stub
		return false;
	}

}
