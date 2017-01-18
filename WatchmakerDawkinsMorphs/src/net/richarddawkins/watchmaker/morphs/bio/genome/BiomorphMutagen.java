package net.richarddawkins.watchmaker.morphs.bio.genome;

import static net.richarddawkins.watchmaker.util.Random.randInt;

import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;

public abstract class BiomorphMutagen implements Mutagen {
	
	static protected SwellType randSwell(SwellType indGene) {
		SwellType swellType;
		switch(indGene) {
		case Shrink: 
				swellType = SwellType.Same;
				break;
		case Same: 
			if(randInt(2) == 1)
				swellType = SwellType.Shrink; 
			else 
				swellType = SwellType.Swell;
			break;
		case Swell: 
			swellType = SwellType.Same;
			break;
		default: 
			swellType = indGene; // Can't happen
		}
		return swellType;
	}


	static public int direction(BiomorphGenome genome) {
		int mutSizeGene = genome.getMutSizeGene().getValue();
		if(randInt(2) == 2) {
			return mutSizeGene;
		} else {
			return -mutSizeGene;
		}		
	}


	static public int direction9() {
		return randInt(2) == 2 ? 1 : -1;
	}
}
