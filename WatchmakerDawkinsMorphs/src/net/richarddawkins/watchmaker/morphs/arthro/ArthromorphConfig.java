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

	public static final String[] SAVED_ARTHROMORPHS = new String[] {
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Anotger ant",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Ant",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Bent legs approaching",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Cartoon cat",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Cyclops",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Earwig",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Fly head on",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Springtail",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Stick Insect",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/Towards Grasshopper",
			"/net/richarddawkins/watchmaker/savedanimals/arthromorphs/water scorpion" };

	@Override
	public String[] getSavedAnimals() {
		return SAVED_ARTHROMORPHS;
	}
	
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
        Morph morph = new Arthromorph();
        initMorph(morph);
        return morph;
    }

}
