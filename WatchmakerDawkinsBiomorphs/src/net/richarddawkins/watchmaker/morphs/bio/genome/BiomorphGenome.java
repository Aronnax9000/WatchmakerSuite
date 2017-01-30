package net.richarddawkins.watchmaker.morphs.bio.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.SimpleGenome;

public abstract class BiomorphGenome extends SimpleGenome {
	


	protected final Gene12345678 gene1 = new Gene12345678(this, "Gene 1");
	protected final Gene12345678 gene2 = new Gene12345678(this, "Gene 2");
	protected final Gene12345678 gene3 = new Gene12345678(this, "Gene 3");
	protected final Gene12345678 gene4 = new Gene12345678(this, "Gene 4");
	protected final Gene12345678 gene5 = new Gene12345678(this, "Gene 5");
	protected final Gene12345678 gene6 = new Gene12345678(this, "Gene 6");
	protected final Gene12345678 gene7 = new Gene12345678(this, "Gene 7");
	protected final Gene12345678 gene8 = new Gene12345678(this, "Gene 8");
	protected final Gene9 gene9 = new Gene9(this, "Gene 9");
	protected final SegNoGene segNoGene = new SegNoGene(this, "Segment Number");
	protected final IntegerGradientGene segDistGene = new IntegerGradientGene(this, "Segment Distance");
	protected final CompletenessGene completenessGene = new CompletenessGene(this, "Completeness");
	protected final SpokesGene spokesGene = new SpokesGene(this, "Spokes");
	protected final IntegerGeneOneOrGreater trickleGene = new IntegerGeneOneOrGreater(this, "Trickle");
	protected final IntegerGeneOneOrGreater mutSizeGene = new IntegerGeneOneOrGreater(this, "Mutation Size");
	protected final IntegerGene1To100 mutProbGene = new IntegerGene1To100(this, "Mutation Probability");



	
	public Gene12345678 getGene1() {
		return gene1;
	}

	public Gene12345678 getGene2() {
		return gene2;
	}

	public Gene12345678 getGene3() {
		return gene3;
	}

	public Gene12345678 getGene4() {
		return gene4;
	}

	public Gene12345678 getGene5() {
		return gene5;
	}

	public Gene12345678 getGene6() {
		return gene6;
	}

	public Gene12345678 getGene7() {
		return gene7;
	}

	public Gene12345678 getGene8() {
		return gene8;
	}

	public Gene9 getGene9() {
		return gene9;
	}

	public SegNoGene getSegNoGene() {
		return segNoGene;
	}

	public IntegerGradientGene getSegDistGene() {
		return segDistGene;
	}

	public CompletenessGene getCompletenessGene() {
		return completenessGene;
	}

	public SpokesGene getSpokesGene() {
		return spokesGene;
	}

	public IntegerGeneOneOrGreater getTrickleGene() {
		return trickleGene;
	}

	public IntegerGeneOneOrGreater getMutSizeGene() {
		return mutSizeGene;
	}

	public IntegerGene1To100 getMutProbGene() {
		return mutProbGene;
	}

    @Override
    public Gene[] toGeneArray() {
        Gene[] theGenes = new Gene[16];

        theGenes[0] = gene1;
        theGenes[1] = gene2;
        theGenes[2] = gene3;
        theGenes[3] = gene4;
        theGenes[4] = gene5;
        theGenes[5] = gene6;
        theGenes[6] = gene7;
        theGenes[7] = gene8;
        theGenes[8] = gene9;
        theGenes[9] = segNoGene;
        theGenes[10] = segDistGene;
        theGenes[11] = completenessGene;
        theGenes[12] = spokesGene;
        theGenes[13] = trickleGene;
        theGenes[14] = mutSizeGene;
        theGenes[15] = mutProbGene;

        return theGenes;
    }

}
