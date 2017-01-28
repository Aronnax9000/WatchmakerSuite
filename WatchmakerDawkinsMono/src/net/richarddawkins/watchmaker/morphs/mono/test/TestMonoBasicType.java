package net.richarddawkins.watchmaker.morphs.mono.test;

import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morphs.bio.geom.Lin;
import net.richarddawkins.watchmaker.morphs.bio.geom.Pic;
import net.richarddawkins.watchmaker.morphs.mono.MonochromeMorphConfig;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoLin;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public class TestMonoBasicType {

	/**
	 * Test harness for Monochrome biomorphs.
	 * 
	 * Creates a minimal monochrome biomorph, retrieves its Phenotype (body),
	 * then prints its list of Lins to the console, together with a
	 * a summary of the phenotype's characteristics. 
	 * 
	 * @author Alan Canon
	 * @param args ignored.
	 */
	public static void main(String[] args) {
		
		// Get a new MonochromeMorphConfig, which is a subclass of 
		// MorphConfig, so it's fine to assign it to a variable of type
		// MorhConfig ("A MonochromeMorphConfig is a MorphConfig.")
		MorphConfig config = new MonochromeMorphConfig();
		
		// Get the MorphConfig to cough up a minimal biomorph.
		// Under the hood, it's actually a MonochromeMorph, but
		// we just assign it to a variable of type Morph, which is fine,
		// because MonochromeMorph is a subclasss of Morph. ("A MonochromeMorph
		// is a Morph.")
		Morph morph = config.newMorph(0);
		
		// Obtain a reference to the morph's body, a collection
		// of drawing primitives and a calculated "margin",
		// which is the bounding rectangle of the morph's body.
		// The concrete class that's returned is actually a subclass 
		// of Phenotype called MonoPic.
		// but here we can deal with it generically ("A MonoPic is a Pic,
		// which is a Phenotype.")
		
		Phenotype phenotype = morph.getPhenotype();
		
		// We've now obtained a phenotype for the morph in a generic way,
		// which would work for any instance of MorphConfig. MorphConfig,
		// Morph, and Phenotype are the top-level interfaces which are 
		// common for all types of Morph: the "outer layers" of the application
		// deal with Morphs using these generic interfaces, since they don't
		// care specifically what kind of morph is being dealt with. In the
		// "inner layers" of the application, where the kind of Morph matters,
		// we use more specific subclasses of MorphConfig, Morph, Phenotype, etc.
		// That kind of work is illustrated below.
		
		// Iterate through the list of lines. We cast the generic Phenotype
		// to its Pic subclass first. Under the hood, the concrete subclass of
		// Phenotype for MonochromeMorph is MonoPic, and its lines colllection
		// are of type MonoLin, a subclass of Lin.
		
		for(Lin lin: ((Pic) phenotype).lines) {

			// We know this is a Monochrome Biomorph so it's safe to cast its
			// Lin instances to instances of the MonoLin subclass of Lin. 
			// If you tried to do this to a ColourLin, you'd get a ClassCastException
			// at runtime.
			MonoLin monoLin = (MonoLin) lin;
			
			// Print out a string representation of the Lin.
			System.out.println(monoLin.toString());
		}
		// How many Lins are in the morph's phenotype? Print it out.
		System.out.println("Morph has " + phenotype.size() + " elements.");
		
		// Get a reference to the bounding rectangle of the morph.
		Rect margin = phenotype.getMargin();
		/*
		 Print out the upper left hand vertex of the rectangle, and its
		 midpoint (Knowing the midpoint is useful for ensuring that the biomorph
		 is drawn at the center of the display area, by calculating the
		 difference between the midpoint of the biomorph and the midpoint of
		 whatever rectangle on the display is going to have the biomorph drawn in it.
		 For example, say the midpoint (Bx, By) of the biomorph is (0, -19) (which
		 it is, in this example) Say you have a display area 100 pixels on a side, so the
		 midpoint (Dx, Dy) is at (50, 50). Then, to move the biomorph so its own
		 midpoint is at the center of the display, you would need to calculate B - D = 
		 (50, 50) - (0, -19) = (50 - 0, 50 - [-19]) = (50, 69). So before plotting each
		 point of the biomorph, it's then necessary to add 50 to the X coordinate and
		 69 to the Y coordinate. If I haven't got my math wrong.
		 */
		System.out.println("Bounding Rectangle: " + margin + " with midpoint " + margin.getMidPoint());
		
	}
}
