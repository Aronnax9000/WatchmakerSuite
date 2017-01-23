package net.richarddawkins.watchmaker.morphs.colour;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.bio.BiomorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.embryo.ColourEmbryology;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourGenome;
import net.richarddawkins.watchmaker.morphs.colour.genome.mutation.ColourAllowedMutations;
import net.richarddawkins.watchmaker.morphs.colour.genome.mutation.ColourMutagen;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class ColourBiomorphConfig extends BiomorphConfig {

	
	@Override
	public Genome newGenome() {
		return new ColourGenome();
	}
	public ColourBiomorphConfig() {
		super();
		setMutagen(new ColourMutagen(new ColourAllowedMutations()));
		embryology = new ColourEmbryology();


	}
	@Override
	public Morph newMorph() {
		Morph morph = new ColourBiomorph();
		wireMorphEvents(morph);
		return morph;
	}
	@Override
	public Phenotype newPhenotype() {
		return new ColourPic();
	}



}
