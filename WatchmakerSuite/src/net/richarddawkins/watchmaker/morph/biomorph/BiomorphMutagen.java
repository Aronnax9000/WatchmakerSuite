package net.richarddawkins.watchmaker.morph.biomorph;

import static net.richarddawkins.watchmaker.morph.util.Random.randInt;

import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.mono.genome.SwellType;


public abstract class BiomorphMutagen 
	implements Mutagen {
	protected SwellType randSwell(SwellType indGene) {
		switch(indGene) {
		case Shrink: return SwellType.Same;
		
		case Same: 
			if(randInt(2) == 1)
				return SwellType.Shrink; 
			else 
				return SwellType.Swell;
		case Swell: return SwellType.Same;
		default: return indGene; // Can't happen
		}
	}


	public int direction(BiomorphGenome genome) {
		int mutSizeGene = genome.getMutSizeGene();
		if(randInt(2) == 2) {
			return mutSizeGene;
		} else {
			return -mutSizeGene;
		}		
	}


	public int direction9() {
		return randInt(2) == 2 ? 1 : -1;
	}

}
