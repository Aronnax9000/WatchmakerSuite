package net.richarddawkins.watchmaker.morph.common;

import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.mono.CompletenessType;
import net.richarddawkins.watchmaker.morph.mono.SpokesType;
import net.richarddawkins.watchmaker.morph.mono.SwellType;

public interface BiomorphGenome extends Genome {
	void makeGenes(int a, int b, int c, int d, int e, int f, int g, int h, int i);
	public int getGene9Max();
	public void setGene9Max(int max);
	public int[] getGene();
	public void setGene(int[] gene);
	public SwellType[] getdGene();
	public void setdGene(SwellType[] dGene);
	public int getSegNoGene();
	public void setSegNoGene(int segNoGene);
	public int getSegDistGene();
	public void setSegDistGene(int segDistGene);
	public CompletenessType getCompletenessGene();
	public void setCompletenessGene(CompletenessType completenessGene);
	public SpokesType getSpokesGene();
	public void setSpokesGene(SpokesType spokesGene);
	public int getTrickleGene();
	public void setTrickleGene(int trickleGene);
	public int getMutSizeGene();
	public void setMutSizeGene(int mutSizeGene);
	public int getMutProbGene();
	public void setMutProbGene(int mutProbGene);
	public void setMorph(Morph morph);
	public void addToMutProbGene(int summand);
	public void addToGene(int index, int summand);
	public void setGene(int index, int value);
	public int getGene(int index);
	public void decrementSegNoGene();
	public void decrementGene(int i);
	public SwellType getDGene(int i);
	public void setDGene(int i, SwellType swellType);
	public void addToMutSizeGene(int summand);
	public void addToTrickleGene(int summand);
	public void addToSegNoGene(int summand);

	void chess();
	void insect();
	void basicTree();
}
