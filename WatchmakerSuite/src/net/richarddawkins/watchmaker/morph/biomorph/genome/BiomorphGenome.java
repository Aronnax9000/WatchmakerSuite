package net.richarddawkins.watchmaker.morph.biomorph.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.SimpleGenome;

public abstract class BiomorphGenome extends SimpleGenome {

	protected int gene9Max = 8;
	protected boolean oddOne;
	protected int order;
	protected int[] gene = new int[9];
	protected SwellType[] dGene = new SwellType[10];
	protected IntegerGene segNoGene = new IntegerGene();
	protected IntegerGene segDistGene = new IntegerGene();
	protected CompletenessGene completenessGene = new CompletenessGene();
	protected SpokesGene spokesGene = new SpokesGene();
	protected IntegerGene trickleGene = new IntegerGene();
	protected IntegerGene mutSizeGene = new IntegerGene();
	protected IntegerGene mutProbGene = new IntegerGene();

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

	public int[] getGene() {
		return gene;
	}

	public void setGene(int[] gene) {
		this.gene = gene;
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

	public void setGene(int index, int value) {
		gene[index] = value;
	}

	public int getGene(int index) {
		return gene[index];
	}

	public SwellType getDGene(int i) {
		return dGene[i];
	}

	public void setDGene(int i, SwellType swellType) {
		dGene[i] = swellType;
	}
	
	public void copy(Genome person) {
		BiomorphGenome child = (BiomorphGenome) person;
		child.setGene(gene.clone());
		child.setDGene(dGene.clone());
		child.getSegNoGene().setValue(getSegNoGene().getValue());
		child.getSegDistGene().setValue(getSegDistGene().getValue());
		child.getCompletenessGene().setValue(getCompletenessGene().getValue());
		child.getSpokesGene().setValue(getSpokesGene().getValue());
		child.getTrickleGene().setValue(getTrickleGene().getValue());
		child.getMutSizeGene().setValue(getMutSizeGene().getValue());
		child.getMutProbGene().setValue(getMutProbGene().getValue());
	}


}
