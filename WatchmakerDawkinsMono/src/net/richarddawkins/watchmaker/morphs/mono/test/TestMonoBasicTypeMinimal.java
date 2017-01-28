package net.richarddawkins.watchmaker.morphs.mono.test;

import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morphs.bio.geom.Lin;
import net.richarddawkins.watchmaker.morphs.mono.MonochromeMorphConfig;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoLin;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoPic;

public class TestMonoBasicTypeMinimal {

	/**
	 * Test harness for Monochrome biomorphs.
	 * 
	 * Creates a minimal monochrome biomorph, retrieves its Phenotype (body),
	 * then prints its list of Lins to the console, together with a
	 * a summary of the phenotype's characteristics. This version is slightly
	 * more terse than TestMonoBasicType. 
	 * @author Alan Canon
	 * @param args ignored.
	 */
	public static void main(String[] args) {
		// In one go, create a new MonochromeMorphConfig, get it to cough up
		// a minimal morph, get its phenotype, and cast it to MonoPic.
		MonoPic monoPic = (MonoPic) new MonochromeMorphConfig().newMorph(0).getPhenotype();
		
		// Run through the list of lines and print out each one.
		for(Lin lin: monoPic.lines) {
			MonoLin monoLin = (MonoLin) lin;
			// You would plot each line to the screen here instead of
			// printing the line's characteristics to the console.
			System.out.println(monoLin.toString());
		}

		// How many lines in the biomorph's body?
		System.out.println("Morph has " + monoPic.size() + " elements.");
		
		// What about the bounding rectangle of the biomorph?
		Rect margin = monoPic.getMargin();
		System.out.println("Bounding Rectangle: " + margin + " with midpoint " + margin.getMidPoint());
		
	}
}
