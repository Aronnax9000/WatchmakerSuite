package net.richarddawkins.wm.morphs.biomorph;

import static net.richarddawkins.wm.Random.randInt;

import net.richarddawkins.wm.morphs.Mutagen;

public abstract class BiomorphMutagen implements Mutagen 
{

	protected SwellType randSwell(SwellType indGene) {
		switch(indGene) {
		case Shrink: return SwellType.Same;
		case Same: return randInt(2) == 1 ? SwellType.Shrink : SwellType.Swell;
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
