package net.richarddawkins.watchmaker.morphs.colour;

import java.util.Vector;

import net.richarddawkins.watchmaker.album.Album;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.embryo.ColourEmbryology;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourGenome;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourGenomeFactory;
import net.richarddawkins.watchmaker.morphs.colour.genome.mutation.ColourAllowedMutations;
import net.richarddawkins.watchmaker.morphs.colour.genome.mutation.ColourMutagen;
import net.richarddawkins.watchmaker.morphs.colour.geom.ColourPic;
import net.richarddawkins.watchmaker.morphs.mono.genome.BiomorphGenomeFactory;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class ColourBiomorphConfig extends SimpleMorphConfig {
	
	@Override
	public Genome newGenome() {
		return new ColourGenome();
	}
	public ColourBiomorphConfig() {
		super();
		this.setStartingMorphBasicType(BiomorphGenomeFactory.RANDOM); // Random
		AllowedMutations muts = new ColourAllowedMutations();
		this.setGenomeFactory(new ColourGenomeFactory(muts));
		this.setMutagen(new ColourMutagen(muts));
		this.setEmbryology(new ColourEmbryology());
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
	@Override
	public Vector<Album> getAlbums() {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
    public String[] getSavedAnimals() {
        // TODO Auto-generated method stub
        return null;
    }
}
