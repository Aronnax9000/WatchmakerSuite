package net.richarddawkins.watchmaker.morph.mono.genome;

import static net.richarddawkins.watchmaker.morph.util.Random.randInt;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphMutagen;
import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SpokesGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SpokesType;
import net.richarddawkins.watchmaker.morph.mono.MonochromeMorphConfig;

public class MonochromeMutagen extends BiomorphMutagen implements Mutagen {
  MonochromeMorphConfig config;
  public MonochromeMutagen(MorphConfig config) {
	  this.config = (MonochromeMorphConfig) config;
  }

  public boolean mutate(Genome genome) {
    MonochromeGenome target = (MonochromeGenome) genome;
    boolean success = false;
    boolean[] mut = target.getMorph().getMorphConfig().getMut();
    int mutProb = target.getMutProbGene().getValue();
    if (mut[6]) {
      if (randInt(100) < mutProb) {
        BiomorphMutagen.addToMutProbGene(target, direction9());
        success = true;
      }
    }
    for (int j = 0; j < 8; j++) {
      if (randInt(100) < mutProb) {
        addToGene(target, j, direction((BiomorphGenome) genome));
        success = true;
      }
    }
    if (randInt(100) < mutProb) {
      addToGene(target, 8, direction9());
      success = true;
    }

    if (mut[0] && randInt(100) < mutProb) {
      int j = direction9();
      addToSegNoGene(target, j);
      success = true;
    }
    if (mut[1] && target.getSegNoGene() > 1) {
      for (int j = 0; j < 7; j++)
        if (randInt(100) < mutProb / 2) {
          target.setDGene(j, randSwell(target.getDGene(j)));
          success = true;
        }
      if (randInt(100) < mutProb / 2) {
        target.setDGene(9, randSwell(target.getDGene(9)));
        success = true;
      }
    }
    if (mut[7] && mut[8] && randInt(100) < mutProb) {
      target.setDGene(8, randSwell(target.getDGene(8)));
      success = true;
    }
    if (mut[0] && target.getSegNoGene() > 1 && randInt(100) < mutProb) {
      target.setSegDistGene(direction9());
      success = true;
    }
    if (mut[2] && randInt(100) < mutProb / 2) {
    	CompletenessGene completenessGene = target.getCompletenessGene();
      completenessGene.setValue(completenessGene.getValue() == CompletenessType.Single
          ? CompletenessType.Double : CompletenessType.Single);
      success = true;
    }
    if (mut[3] && randInt(100) < mutProb / 2) {
    	SpokesGene spokesGene = target.getSpokesGene();
      switch (spokesGene.getValue()) {
      case NorthOnly:
    	  spokesGene.setValue(SpokesType.NSouth);
        break;
      case NSouth:
    	  spokesGene.setValue(direction9() == 1 ? SpokesType.Radial : SpokesType.NorthOnly);
        break;
      case Radial:
    	  spokesGene.setValue(SpokesType.NSouth);
      }
      success = true;
    }
    if (mut[4] && randInt(100) < Math.abs(mutProb)) {
      addToTrickleGene(target, direction9());
      success = true;
    }
    if (mut[5] && randInt(100) < Math.abs(mutProb)) {
      addToMutSizeGene(target, direction9());
      success = true;
    }
    return success;
  }

@Override
public void setMorphConfig(MorphConfig morphConfig) {
	this.config = (MonochromeMorphConfig) morphConfig;
	
}

@Override
public MorphConfig getMorphConfig() {
	return config;

}

}
