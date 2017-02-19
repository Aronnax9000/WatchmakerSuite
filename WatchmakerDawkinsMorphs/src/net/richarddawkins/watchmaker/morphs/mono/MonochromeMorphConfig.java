package net.richarddawkins.watchmaker.morphs.mono;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.Triangler;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morphs.mono.embryo.MonochromeEmbryology;
import net.richarddawkins.watchmaker.morphs.mono.genome.BiomorphGenomeFactory;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenomeFactory;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeTriangler;
import net.richarddawkins.watchmaker.morphs.mono.genome.mutation.MonochromeAllowedMutations;
import net.richarddawkins.watchmaker.morphs.mono.genome.mutation.MonochromeMutagen;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class MonochromeMorphConfig extends SimpleMorphConfig {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.mono.MonochromeMorphConfig");

	@Override
	public Genome newGenome() {
		return new MonochromeGenome();
	}

	@Override
	public Triangler getTriangler() { return MonochromeTriangler.getInstance(); }
	
	public MonochromeMorphConfig() {
		this.setStartingMorphBasicType(BiomorphGenomeFactory.BASIC_TREE);
		this.setAllowedMutations(new MonochromeAllowedMutations());
		this.setMutagen(new MonochromeMutagen(allowedMutations));
		this.setGenomeFactory(new MonochromeGenomeFactory(allowedMutations));
		this.setEmbryology(new MonochromeEmbryology());
	}

	@Override
	public Phenotype newPhenotype() {
		return new MonoPic();
	}
	
	@Override
	public Morph newMorph() {
		Morph morph = new MonochromeMorph();
		wireMorphEvents(morph);
		return morph;
	}

	public static final String[] SAVED_MONOCHROME = new String[] {
			"/net/richarddawkins/watchmaker/savedanimals/monochrome/Handkerchief with bows",
			"/net/richarddawkins/watchmaker/savedanimals/monochrome/Stunted",
			"/net/richarddawkins/watchmaker/savedanimals/monochrome/Chinese character",
			"/net/richarddawkins/watchmaker/savedanimals/monochrome/Exhibition zoo",
			"/net/richarddawkins/watchmaker/savedanimals/monochrome/Alphabet zoo"
	};

	@Override
	public String[] getSavedAnimals() { return SAVED_MONOCHROME; }

	
}
