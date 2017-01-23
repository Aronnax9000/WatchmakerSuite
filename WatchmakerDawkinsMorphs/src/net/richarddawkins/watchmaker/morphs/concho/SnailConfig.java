package net.richarddawkins.watchmaker.morphs.concho;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.Mutagen;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailAllowedMutations;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailEmbryology;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenome;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailMutagen;
import net.richarddawkins.watchmaker.morphs.concho.geom.SnailPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class SnailConfig extends SimpleMorphConfig {
	@Override
	public Genome newGenome() {
		
		return new SnailGenome();
	}
	protected boolean sideView = false;
	@Override
	public Phenotype newPhenotype() {
		return new SnailPic();
	}
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

	public SnailConfig() {
		mutagen = new SnailMutagen(new SnailAllowedMutations());
		embryology = new SnailEmbryology();
		setDefaultBreedingRows(3);
		setDefaultBreedingCols(5);
	}

	@Override
	public Morph newMorph() {
		return new Snail(this);
	}


}
