package net.richarddawkins.watchmaker.morphs.mono;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.bio.BiomorphConfig;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeEmbryology;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeMutagen;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class MonochromeMorphConfig extends BiomorphConfig {
	@Override
	public Genome newGenome() {
		return new MonochromeGenome();
	}
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
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (MonochromeMutagen) mutagen;

	}
	@Override
	public Phenotype newPhenotype() {
		return new MonoPic();
	}
	@Override
	public Morph newMorph() {
		Morph morph = new MonochromeMorph(this);
		return morph;
	}
}
