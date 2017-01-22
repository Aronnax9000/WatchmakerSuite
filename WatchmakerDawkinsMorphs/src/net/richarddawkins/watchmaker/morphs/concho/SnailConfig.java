package net.richarddawkins.watchmaker.morphs.concho;

import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailAllowedMutations;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailEmbryology;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailMutagen;

public class SnailConfig extends SimpleMorphConfig {

	protected boolean sideView = false;

	protected SnailMutagen mutagen;

	@Override
	public Mutagen getMutagen() {
		return mutagen;
	}

	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (SnailMutagen) mutagen;
	}

	public boolean isSideView() {
		return sideView;
	}

	public void setSideView(boolean sideView) {
		this.sideView = sideView;
	}

	@Override
	public Morph createMorph(int type) {
		return new Snail(this, type);
	}
	// private SnailMenuBuilder menuBuilder = new SnailMenuBuilder(this);

	public SnailConfig() {
		mutagen = new SnailMutagen(new SnailAllowedMutations());
		embryology = new SnailEmbryology();
		
		setDefaultBreedingRows(3);
		setDefaultBreedingCols(5);
	}


}
