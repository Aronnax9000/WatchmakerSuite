package net.richarddawkins.watchmaker.morphs.arthro.embryo;

import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.embryo.SimpleEmbryology;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphGenome;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthromorphPic;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class NewArthromorphEmbryology extends SimpleEmbryology implements Embryology {

	@Override
	public void develop(Genome genome, Phenotype phenotype) {
		this.develop(genome, phenotype);
		ArthromorphGenome genes = (ArthromorphGenome) genome;
		ArthromorphPic pic = (ArthromorphPic) phenotype;
		
	}

}
