package net.richarddawkins.watchmaker.morphs.arthro;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.embryo.ArthromorphEmbryology;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphGenome;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphGenomeFactory;
import net.richarddawkins.watchmaker.morphs.arthro.genome.mutation.ArthromorphAllowedMutations;
import net.richarddawkins.watchmaker.morphs.arthro.genome.mutation.ArthromorphMutagen;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthromorphPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class ArthromorphConfig extends SimpleMorphConfig {
	
	public ArthromorphConfig() {
		super();
		this.setStartingMorphBasicType(GenomeFactory.BASIC_TREE);
		this.setAllowedMutations(new ArthromorphAllowedMutations());
		this.setMutagen(new ArthromorphMutagen(this.allowedMutations));
		this.setGenomeFactory(new ArthromorphGenomeFactory(this.allowedMutations));
		this.embryology = new ArthromorphEmbryology();
	}
	
	@Override
	public Phenotype newPhenotype() {
		return new ArthromorphPic();
	}
	
	@Override
	public Genome newGenome() {
		return new ArthromorphGenome();
	}

	@Override
	public Morph newMorph() {
		Arthromorph arthromorph = new Arthromorph();
		wireMorphEvents(arthromorph);
		return arthromorph;
	}

}
