package net.richarddawkins.watchmaker.morphs.mono;

import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.bio.BiomorphConfig;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeEmbryology;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeMutagen;

public class MonochromeMorphConfig extends BiomorphConfig {

	protected MonochromeMutagen mutagen;

	public Mutagen getMutagen() {
		return mutagen;
	}

	public MonochromeMorphConfig() {
		mutagen = new MonochromeMutagen(new MonochromeAllowedMutations());
		embryology = new MonochromeEmbryology();
		
		geneBoxCount = 16;
		setDefaultBreedingRows(3);
		setDefaultBreedingCols(5);
	}

	@Override
	public Morph createMorph(int type) {
		return (Morph) new MonochromeMorph(this, type);
		
	}


	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (MonochromeMutagen) mutagen;

	}
}
