package net.richarddawkins.watchmaker.morphs.arthro.genome;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.morph.Morph;

public class ArthromorphGenome extends SimpleGenome {
	protected Atom animalTrunk;

	protected int atomCount;
	public ArthromorphGenome() {

	}

	public void copy(Genome target) {
		ArthromorphGenome genome = (ArthromorphGenome) target;
		((ArthromorphGenome) target).setAnimalTrunk(animalTrunk.copy());
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
		return new Gene[] { animalTrunk };
	}


}
