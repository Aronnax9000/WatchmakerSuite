package net.richarddawkins.watchmaker.morphs.concho.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.GenomeFactory;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.morphs.concho.genome.type.ClassicSnail;

public class SnailGenomeFactory extends GenomeFactory {

	public SnailGenomeFactory(AllowedMutations muts) {
		super(muts);
	}

	@Override
	public Genome getBasicType(int i) {
		SnailGenome genome = new SnailGenome();
		ClassicSnail snail = ClassicSnail.values()[i];
		genome.getOpening().setValue(snail.getOpening());
		genome.getDisplacement().setValue(snail.getDisplacement());
		genome.getShape().setValue(snail.getShape());
		genome.getTranslation().setValue(snail.getTranslation());
		genome.getCoarsegraininess().setValue(snail.getCoarsegraininess());
		genome.getReach().setValue(snail.getReach());
		genome.getGeneratingCurve().setValue(snail.getGeneratingCurve());
		genome.getTranslationGradient().setValue(snail.getTranslationGradient());
//		genome.getGradient.setValue(snail.getGradient()); Dawkins never set dGradient in his standard types.
		genome.getHandedness().setValue(snail.getHandedness());
		genome.getMutProb().setValue(snail.getMutProb());	
		return genome;
	}
	
	@Override
	public Genome deliverSaltation() {
		// TODO Auto-generated method stub
		return null;
	}

}
