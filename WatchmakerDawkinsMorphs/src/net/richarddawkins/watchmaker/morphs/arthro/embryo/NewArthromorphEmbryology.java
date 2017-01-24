package net.richarddawkins.watchmaker.morphs.arthro.embryo;

import net.richarddawkins.watchmaker.embryo.Embryology;
import net.richarddawkins.watchmaker.embryo.SimpleEmbryology;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphGenome;
import net.richarddawkins.watchmaker.morphs.arthro.phenotype.ArthromorphPic;

public class NewArthromorphEmbryology extends SimpleEmbryology implements Embryology {

	@Override
	public void develop(Morph morph) {
		this.develop(morph);
		ArthromorphGenome genes = (ArthromorphGenome) morph.getGenome();
		ArthromorphPic pic = (ArthromorphPic) morph.getPic();
	}
}
