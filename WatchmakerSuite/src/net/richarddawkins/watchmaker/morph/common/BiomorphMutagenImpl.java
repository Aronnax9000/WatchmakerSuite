package net.richarddawkins.watchmaker.morph.common;

import static net.richarddawkins.watchmaker.morph.util.Random.randInt;

import net.richarddawkins.watchmaker.morph.mono.SwellType;


public abstract class BiomorphMutagenImpl 
	implements BiomorphMutagen {
	protected SwellType randSwell(SwellType indGene) {
		switch(indGene) {
		case Shrink: return SwellType.Same;
		case Same: return randInt(2) == 1 ? SwellType.Shrink : SwellType.Swell;
		case Swell: return SwellType.Same;
		default: return indGene; // Can't happen
		}
	}

	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.biomorph.impl.BiomorphMutagen#direction()
	 */
	@Override
	public int direction(BiomorphGenome genome) {
		int mutSizeGene = genome.getMutSizeGene();
		if(randInt(2) == 2) {
			return mutSizeGene;
		} else {
			return -mutSizeGene;
		}		
	}

	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.watchmaker.morphs.biomorph.impl.BiomorphMutagen#direction9()
	 */
	@Override
	public int direction9() {
		return randInt(2) == 2 ? 1 : -1;
	}

}
