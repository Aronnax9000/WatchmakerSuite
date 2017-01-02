package net.richarddawkins.watchmaker.morphs.arthro;

import java.awt.Dimension;
import java.awt.Graphics2D;

import net.richarddawkins.watchmaker.morphs.Person;

public interface ArthromorphPerson extends Person {
	
	public int getNorthPole();

	public void setNorthPole(int northPole);

	public int getSouthPole();

	public void setSouthPole(int southPole);
	
	public int getEastPole();

	public void setEastPole(int eastPole);

	public int getWestPole();

	public void setWestPole(int westPole);

	public void expandPoles(int north, int south, int east, int west);
	
	public void minimalAnimal();
	
	public int getGradient();


	public void setGradient(int gradient);	
	public int getAtomCount();
	public void setAtomCount(int atomCount);
	public Atom getAnimalTrunk();
	void setAnimalTrunk(Atom animalTrunk);
	double[] getCumParams();

	void setCumParams(double[] cumParams);
	void drawInBox(Graphics2D g2, Dimension panelSize, boolean midBox) throws ArthromorphGradientExceeds1000Exception;

	
}
