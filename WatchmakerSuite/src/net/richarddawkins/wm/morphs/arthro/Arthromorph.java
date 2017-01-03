package net.richarddawkins.wm.morphs.arthro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Vector;

import net.richarddawkins.watchmaker.draw.DrawingPrimitive;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.common.geom.Pic;
import net.richarddawkins.wm.morphs.SimpleMorph;

/**
 * <h2>Original documentation from Arthromorphs/MyGlobals</h2>
 * <p>Arthromorphs   by Richard Dawkins and Ted Kaehler</p>
 * <p>Ted's initial version: 25 Nov 90</p>
 * <p>Current version: 8 Dec 90</p>
 * <p>Since we both are confused by handles and pointers in Pascal, this does not use any of either!</p>

 * <p>There is a Record called Atom that holds a little part of an animal.  It has fields for a Height, 
 * a Width, and an Angle.
 * <ul>
 *  <li>When it is used to describe a Segment, Height and Width are for the oval,
 *    and Angle is not used</li>
 *  <li>When it is used to describe a Joint, Height is the thickness of the leg-part,
 *    Width is the length, and Angle is the angle from the previous joint</li>
 *  <li>When it is used to describe a Claw,  Height is the thickness of the claw-part,
 *    Width is the length, and Angle is the between the claw halves</li>
 * </ul>
 * </p>
 * <p>
 * Remember that the true Joint length is the multiplication of all the factors:
 * The Animal's joint length, this Section's joint length, this Segment's joint length, and the Joint's own joint length.
 * Thus a Segment actually has three parts: its factor for Segment size, its 
 *  factor for Joint size, and its factor for Claw size.  Each of these are Atoms.  Thus a
 *  Segment has three Atoms.  They are distinguished by having different kinds: SegmentTrunk,
 *  SegmentJoint, and SegmentClaw.
 * </p>
 * <p>
 * An Animal-record also has three Atoms in it AnimalTrunk, AnimalJoint, and AnimalClaw.
 * </p>
 * <p>
 * How are Atoms hooked together?  Here is a sample Animal.  Each line is an Atom, but I don't
 *  show the values inside it, like Height: 20 Width: 30, etc.
 * <pre>
 * AnimalTrunk
 *  AnimalJoint
 *  AnimalClaw
 *    SectionTrunk
 *      SectionJoint
 *      SectionClaw
 *        SegmentTrunk
 *          SegmentJoint
 *          SegmentClaw
 *            Joint
 *            Joint
 *            Joint
 *              Claw
 *        SegmentTrunk
 *          SegmentJoint
 *          SegmentClaw
 *            Joint
 *            Joint
 *            Joint
 *              Claw
 * </pre>
 * </p>
 * <p>
 * A Section sets the tone for all segments within it:  Head, Thorax, Abdomen are sections
 *</p>
 *<p>
 * In the above set of Atoms, there are two fields for connecting Atoms together.
 *<ul>
 *  <li>NextLikeMe  hooks the atom to the next atom on the same level.</li>
 *  <li>FirstBelowMe  hooks the atom to the first atom on a lower level.</li>
 * </ul>
 * </p>
 * <p>
 * Look at the diagram above.  When an atom points to another with NextLikeMe, they
 * have the same level of indentation.  When an atom points to another with 
 * FirstBelowMe, the atom is indented one more level.
 * </p>
 * <p>
 * The first SegmentTrunk points way down to the second SegmentTrunk with NextLikeMe.
 * </p>
 * <p>
 * The Joints point to the next with NextLikeMe.  However, the AnimalClaw
 * points to SegmentTrunk using FirstBelowMe.  Note that the three atoms that
 * make up an Animal are split.  AnimalJoint is pointed to with FirstBelowMe even
 * though it is part of the animal description.  I had to do this so that AnimalTrunk could use its
 * NextLikeMe to point at the next animal.  Likewise with Segments.
 * </p>
 * <p>All atoms are stored in a big Array called the BoneYard.  You find an atom
 * by knowing its index (the integer that is its place in the array).  The two "pointers" NextLikeMe 
 * and FirstBelowMe are not pointers at all, but simply integers.</p>
 * <p>
 * An individual Animal can have its atoms spread out all over the BoneYard, but 
 * each atom in it holds the index of the next atom in it.  Thus we can walk down 
 * the parts of an animal very easily.  Atoms that are not being used are labelled Free.</p>
 * @author Richard Dawkins, Ted Kaehler, Alan Canon
 *
 */
public class Arthromorph extends SimpleMorph implements Cloneable {
  protected ArthromorphConfig config;

  
  protected ArthromorphGenome genome;
  
  public ArthromorphGenome getGenome() {
    return genome;
  }
  @Override
  public void setGenome(Genome genome) {
    this.genome = (ArthromorphGenome) genome;
  }

  @Override
  public void setMorphConfig(MorphConfig config) {
    this.config = (ArthromorphConfig) config;
  }

  @Override
  public MorphConfig getMorphConfig() {
    return config;
  }
	
	
	public Arthromorph(MorphConfig config, int basicType) {
		this.config = (ArthromorphConfig) config;
		ArthromorphGenome newGenome = new ArthromorphGenome(this);
		this.genome = newGenome;
		genome.setBasicType(basicType);
	}

	public Arthromorph(ArthromorphConfig config) {
		this.config = (ArthromorphConfig) config;
	}
	@Override
	public void draw(Graphics2D g2, Dimension d, boolean midBox) {
		g2.setColor(Color.BLACK);
//		g2.drawString("Offspring " + this.getOffspringCount(false), 10, 20);
//		g2.drawString(d.getWidth() + "x" + d.getHeight(), 10, 40);
		try {
			((ArthromorphGenome)genome).drawInBox(g2, d, midBox);
		} catch (ArthromorphGradientExceeds1000Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void delayvelop(Graphics2D g2, Dimension d, boolean midBox) {
		
		
	}

	@Override
	public Morph reproduce() {
		Arthromorph child = new Arthromorph(config);
		child.setGenome(genome.reproduce(child));
		child.setParent(this);
		return child;
	}
	@Override
	public void generatePrimitives(Vector<DrawingPrimitive> primitives, Point centre) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setPic(Pic pic) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Pic getPic() {
		// TODO Auto-generated method stub
		return null;
	}


	

	
}
