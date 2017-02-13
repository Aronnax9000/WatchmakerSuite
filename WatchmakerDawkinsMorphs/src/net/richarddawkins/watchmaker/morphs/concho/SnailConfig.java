package net.richarddawkins.watchmaker.morphs.concho;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morphs.concho.embryo.SnailEmbryology;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenome;
import net.richarddawkins.watchmaker.morphs.concho.genome.SnailGenomeFactory;
import net.richarddawkins.watchmaker.morphs.concho.genome.mutation.SnailAllowedMutations;
import net.richarddawkins.watchmaker.morphs.concho.genome.mutation.SnailMutagen;
import net.richarddawkins.watchmaker.morphs.concho.geom.SnailPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class SnailConfig extends SimpleMorphConfig {
	public static final String[] SAVED_SNAILS = new String[] {
		"/net/richarddawkins/watchmaker/savedanimals/snails/Antelope horn",
//		"/net/richarddawkins/watchmaker/savedanimals/snails/Antelope horns",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Bubble",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Glory of the Sea",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Ivory Cone",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Large Album",
//		"/net/richarddawkins/watchmaker/savedanimals/snails/Modified whelk",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Moon",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Oyster",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Shell Album 102",
//		"/net/richarddawkins/watchmaker/savedanimals/snails/Shell Album 420",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Solid Pupa",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Spirula",
		"/net/richarddawkins/watchmaker/savedanimals/snails/Square snail",
		"/net/richarddawkins/watchmaker/savedanimals/snails/SQUASHED AMMONITE",
		"/net/richarddawkins/watchmaker/savedanimals/snails/TEST ALBUM",
		"/net/richarddawkins/watchmaker/savedanimals/snails/White Pacific Atys"
	};

	@Override
	public String[] getSavedAnimals() {
		return SAVED_SNAILS;
	}
	
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
