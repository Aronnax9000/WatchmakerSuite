package net.richarddawkins.watchmaker.morphs.arthro.genome;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.arthro.genome.type.AtomKind;

public class ArthromorphGenome extends SimpleGenome {
	
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.arthro.genome.ArthromorphGenome");
	
	protected Atom animalTrunk;

	protected int atomCount;
	public ArthromorphGenome() {	}

	public void copy(Genome target) {
		ArthromorphGenome genome = (ArthromorphGenome) target;
		((ArthromorphGenome) target).setAnimalTrunk(animalTrunk.copy(target));
	}

	public Atom getAnimalTrunk() {
		return animalTrunk;
	}

	public int getAtomCount() {
		return atomCount;
	}
	
	public Genome reproduce(Morph newMorph) {
		ArthromorphGenome child = new ArthromorphGenome();
		// A bit of a cheat, because reproduce needs access to the Config
		// object.
		copy(child);
		
		return child;
	}

	public void setAnimalTrunk(Atom animalTrunk) {
		this.animalTrunk = animalTrunk;
	}

	public void setAtomCount(int atomCount) {
		this.atomCount = atomCount;
	}

	
	@Override
	public Gene[] toGeneArray() {
		return animalTrunk.toVector().toArray(new Gene[0]);
	}

	@Override
	public void readFromByteBuffer(ByteBuffer byteBuffer) {
		Vector<Atom> atoms = new Vector<Atom>();
		Vector<short[]> atomLinks = new Vector<short[]>();
		int atomNumber = 0;
		while(byteBuffer.hasRemaining()) {
			Atom atom = new Atom(this);
			atom.readValueFromByteBuffer(byteBuffer);
			atoms.add(atom);
			short[] atomLink = new short[]{
					byteBuffer.getShort(), //firstBelowMe
					byteBuffer.getShort() //nextLikeMe
			};
			logger.info(atomNumber + ":" + atom.toString() + " fbm:" + atomLink[0] + " nlm: " + atomLink[1]);
			atomNumber++;
			atomLinks.add(atomLink);
		}
		Iterator<short[]> linksIter = atomLinks.iterator();
		
		for(Atom atom: atoms) {
			short[] atomLink = linksIter.next();
			if(atomLink[1] != 0)
				atom.firstBelowMe = atoms.elementAt(atomLink[1] - 1);
			if(atom.kind == AtomKind.AnimalTrunk) {
				atom.gradientGene = atomLink[0];
			} else {
				if(atomLink[0] != 0)
				atom.nextLikeMe = atoms.elementAt(atomLink[0] - 1);
			}
		}
		animalTrunk = atoms.firstElement();
	}

	@Override
	public void writeToByteBuffer(ByteBuffer byteBuffer) {
		
	}

}
