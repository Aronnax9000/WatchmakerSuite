package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.morphs.bio.Biomorph;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;
import net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome;

public abstract class BiomorphGenomeFactory extends GenomeFactory {
	
	public BiomorphGenomeFactory(AllowedMutations muts) {
		super(muts);
	}

	@Override
	public Genome getBasicType(int basicType) {
		switch (basicType) {
		case RANDOM:
			return deliverSaltation();
		case BASIC_TREE:
			return basicTree();
		case CHESS:
			return chess();
		case INSECT:
			return insect();
		default:
			return null;
		}
	}

	protected void makeGenes(MonochromeGenome genome, int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		Gene[] myGenes = genome.toGeneArray();
		for (int j = 0; j < 8; j++) {
			IntegerGradientGene myGene = (IntegerGradientGene) myGenes[j];
			myGene.setGradient(SwellType.Same);
		}
		genome.getSegDistGene().setGradient(SwellType.Same);
		genome.getSegNoGene().setValue(1);
		genome.getCompletenessGene().setValue(CompletenessType.Double);
		genome.getSpokesGene().setValue(SpokesType.NorthOnly);
		genome.getTrickleGene().setValue(Biomorph.TRICKLE);
		genome.getMutSizeGene().setValue(Biomorph.TRICKLE / 2);
		genome.getMutProbGene().setValue(10);

		genome.getGene1().setValue(a);
		genome.getGene2().setValue(b);
		genome.getGene3().setValue(c);
		genome.getGene4().setValue(d);
		genome.getGene5().setValue(e);
		genome.getGene6().setValue(f);
		genome.getGene7().setValue(g);
		genome.getGene8().setValue(h);
		genome.getGene9().setValue(i);

	}
	
	abstract protected Genome insect();


	abstract protected Genome chess();

	abstract protected Genome basicTree();

}
