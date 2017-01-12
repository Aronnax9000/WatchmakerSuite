package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morph.biomorph.Biomorph;

public abstract class BiomorphGenome extends SimpleGenome {

	protected int gene9Max = 8;
	protected boolean oddOne;
	protected int order;
	protected IntegerGradientGene gene1 = new IntegerGradientGene("Gene 1");
	protected IntegerGradientGene gene2 = new IntegerGradientGene("Gene 2");
	protected IntegerGradientGene gene3 = new IntegerGradientGene("Gene 3");
	protected IntegerGradientGene gene4 = new IntegerGradientGene("Gene 4");
	protected IntegerGradientGene gene5 = new IntegerGradientGene("Gene 5");
	protected IntegerGradientGene gene6 = new IntegerGradientGene("Gene 6");
	protected IntegerGradientGene gene7 = new IntegerGradientGene("Gene 7");
	protected IntegerGradientGene gene8 = new IntegerGradientGene("Gene 8");
	protected IntegerGradientGene gene9 = new IntegerGradientGene("Gene 9");

	protected SwellType[] dGene = new SwellType[10];
	protected IntegerGene segNoGene = new IntegerGene("Segment Number");
	protected IntegerGene segDistGene = new IntegerGene("Segment Distance");
	protected CompletenessGene completenessGene = new CompletenessGene("Completeness");
	protected SpokesGene spokesGene = new SpokesGene("Spokes");
	protected IntegerGene trickleGene = new IntegerGene("Trickle");
	protected IntegerGene mutSizeGene = new IntegerGene("Mutation Size");
	protected IntegerGene mutProbGene = new IntegerGene("Mutation Probability");

	abstract public void basicTree();

	abstract public void chess();

	abstract public void insect();

	public void setBasicType(int basicType) {
		switch (basicType) {
		case 1:
			basicTree();
			break;
		case 2:
			chess();
			break;
		case 3:
			insect();
			break;
		default:
			break;
		}
	}



	public SwellType[] getDGene() {
		return dGene;
	}

	public void setDGene(SwellType[] dGene) {
		this.dGene = dGene;
	}

	public IntegerGene getSegNoGene() {
		return segNoGene;
	}

	public void setSegNoGene(IntegerGene segNoGene) {
		this.segNoGene = segNoGene;
	}

	public IntegerGene getSegDistGene() {
		return segDistGene;
	}

	public void setSegDistGene(IntegerGene segDistGene) {
		this.segDistGene = segDistGene;
	}

	public CompletenessGene getCompletenessGene() {
		return completenessGene;
	}

	public void setCompletenessGene(CompletenessGene completenessGene) {
		this.completenessGene = completenessGene;
	}

	public SpokesGene getSpokesGene() {
		return spokesGene;
	}

	public void setSpokesGene(SpokesGene spokesGene) {
		this.spokesGene = spokesGene;
	}

	public IntegerGene getTrickleGene() {
		return trickleGene;
	}

	public void setTrickleGene(IntegerGene trickleGene) {
		this.trickleGene = trickleGene;
	}

	public IntegerGene getMutSizeGene() {
		return mutSizeGene;
	}

	public void setMutSizeGene(IntegerGene mutSizeGene) {
		this.mutSizeGene = mutSizeGene;
	}

	public IntegerGene getMutProbGene() {
		return mutProbGene;
	}

	public void setMutProbGene(IntegerGene mutProbGene) {
		this.mutProbGene = mutProbGene;
	}

	public int getGene9Max() {
		return gene9Max;
	}

	public void setGene9Max(int max) {
		gene9Max = max;
	}



	public SwellType getDGene(int i) {
		return dGene[i];
	}

	public void setDGene(int i, SwellType swellType) {
		dGene[i] = swellType;
	}

	public void copy(Genome destinationGenome) {
		BiomorphGenome child = (BiomorphGenome) destinationGenome;
		gene1.copy(child.gene1);
		gene2.copy(child.gene2);
		gene3.copy(child.gene3);
		gene4.copy(child.gene4);
		gene5.copy(child.gene5);
		gene6.copy(child.gene6);
		gene7.copy(child.gene7);
		gene8.copy(child.gene8);
		gene9.copy(child.gene9);
		child.setDGene(dGene.clone());
		child.getSegNoGene().setValue(getSegNoGene().getValue());
		child.getSegDistGene().setValue(getSegDistGene().getValue());
		child.getCompletenessGene().setValue(getCompletenessGene().getValue());
		child.getSpokesGene().setValue(getSpokesGene().getValue());
		child.getTrickleGene().setValue(getTrickleGene().getValue());
		child.getMutSizeGene().setValue(getMutSizeGene().getValue());
		child.getMutProbGene().setValue(getMutProbGene().getValue());
	}

	public void makeGenes(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		for (int j = 0; j < 10; j++) {
		      dGene[j] = SwellType.Same;
		    }
		    segNoGene.setValue(1);
		    completenessGene.setValue(CompletenessType.Double);
		    spokesGene.setValue(SpokesType.NorthOnly);
		    trickleGene.setValue(Biomorph.TRICKLE);
		    mutSizeGene.setValue(Biomorph.TRICKLE / 2);
		    mutProbGene.setValue(10);
 
		gene1.setValue(a);
		    gene2.setValue(b);
		    gene3.setValue(c);
		    gene4.setValue(d);
		    gene5.setValue(e);
		    gene6.setValue(f);
		    gene7.setValue(g);
		    gene8.setValue(h);
		    gene9.setValue(i);
		
	}



}
