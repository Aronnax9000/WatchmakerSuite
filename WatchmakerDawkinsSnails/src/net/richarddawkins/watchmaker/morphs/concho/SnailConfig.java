package net.richarddawkins.watchmaker.morphs.concho;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailAllowedMutations;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailEmbryology;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenome;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenomeFactory;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailMutagen;
import net.richarddawkins.watchmaker.morphs.concho.geom.SnailPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class SnailConfig extends SimpleMorphConfig {
	@Override
	public Genome newGenome() {
		return new SnailGenome();
	}
	@Override
	public Phenotype newPhenotype() {
		return new SnailPic();
	}


	public SnailConfig() {
		this.setAllowedMutations(new SnailAllowedMutations());
		this.setMutagen(new SnailMutagen(this.allowedMutations));
		this.setGenomeFactory(new SnailGenomeFactory(this.allowedMutations));
		this.setEmbryology(new SnailEmbryology());
	}
	@Override
	public Morph newMorph() {
		Morph morph = new Snail();
		wireMorphEvents(morph);
		return morph;
	}
}
