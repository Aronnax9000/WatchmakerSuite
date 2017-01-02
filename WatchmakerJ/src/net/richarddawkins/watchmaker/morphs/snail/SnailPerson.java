package net.richarddawkins.watchmaker.morphs.snail;

import net.richarddawkins.watchmaker.morphs.Person;

public interface SnailPerson extends Person {
	public double getwOpening();

	public void setwOpening(double wOpening);

	public double getdDisplacement();
	public void setdDisplacement(double dDisplacement);
	public double getsShape();
	public void setsShape(double sShape);

	public double gettTranslation();

	public void settTranslation(double tTranslation);

	public double getTranslationGradient();

	public void setTranslationGradient(double translationGradient);

	public double getdGradient();

	public void setdGradient(double dGradient);
	public int getCoarsegraininess();
	public void setCoarsegraininess(int coarsegraininess);
	public int getReach();
	public void setReach(int reach);

	public int getGeneratingCurve();

	public void setGeneratingCurve(int generatingCurve);

	public int getHandedness();

	public void setHandedness(int handedness);
	public int getMutProb();

	public void setMutProb(int mutProb);
	public void addToDDisplacement(double summand);
	public void addToTTranslation(double summand);
	public void flipHandedness();
}
