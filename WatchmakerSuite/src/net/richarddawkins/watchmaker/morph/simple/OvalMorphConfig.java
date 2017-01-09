package net.richarddawkins.watchmaker.morph.simple;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;

public class OvalMorphConfig extends SimpleMorphConfig {

	public OvalMorphConfig(MorphType morphType) {
		super(morphType);
	}

	protected OvalMorphMutagen mutagen;
	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (OvalMorphMutagen) mutagen;

	}

	@Override
	public Mutagen getMutagen() {
		return mutagen;
	}

	@Override
	public boolean[] getMut() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Morph createMorph(int type) {
		OvalMorph morph = new OvalMorph();
		return morph;
	}

}
