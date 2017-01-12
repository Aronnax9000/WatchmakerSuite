package net.richarddawkins.watchmaker.morph.colour.genome;

import static net.richarddawkins.watchmaker.morph.util.Random.randInt;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphMutagen;
import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SpokesGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SpokesType;
import net.richarddawkins.watchmaker.morph.colour.ColourBiomorphConfig;

public class ColourMutagen extends BiomorphMutagen {
  protected ColourBiomorphConfig config;
  public void setMorphConfig(MorphConfig config) {
	  this.config = (ColourBiomorphConfig) config;
  }
  public MorphConfig getMorphConfig() { return config; }
  
  
  public ColourMutagen(ColourBiomorphConfig config) {
    this.config = (ColourBiomorphConfig) config;
  }

  LimbType randLimbType() {
    switch (randInt(3)) {
    case 1:
      return LimbType.Stick;
    case 2:
      return LimbType.Oval;
    case 3:
      return LimbType.Rectangle;
    default:
      return null;
    }
  }

  LimbFillType randLimbFill() {
    switch (randInt(2)) {
    case 1:
      return LimbFillType.Open;
    case 2:
      return LimbFillType.Filled;
    default:
      return null;
    }
  }

  public boolean mutate(Genome genome) {
    ColourGenome target = (ColourGenome) genome;
    boolean success = false;
    boolean[] mut = config.getMut();
    int mutProb = target.getMutProbGene().getValue();
    if (mut[6] && randInt(100) < mutProb) {
      addToMutProbGene(target, direction9());
      // Update local copy of MutProbGene
      mutProb = target.getMutProbGene().getValue();
      success = true;
    }
    if (mut[12]) {
      for (int j = 0; j < 8; j++)
        if (randInt(100) < mutProb) {
          addToGene(target, j, direction((BiomorphGenome)genome));
          success = true;
        }
      if (randInt(100) < mutProb) {
    	addToGene(target, 8, direction9());
        success = true;
      }

    }
    if (mut[9])
      for (int j = 0; j < 8; j++)
        if (randInt(100) < mutProb) {
          target.addToColorGene(j, direction9());
          success = true;
        }
    if (mut[7] && randInt(100) < mutProb) {
      target.setLimbShapeGene(randLimbType());
      success = true;
    }
    if (mut[8] && randInt(100) < mutProb) {
      target.setLimbFillGene(randLimbFill());
      success = true;
    }

    if (mut[10] && randInt(100) < mutProb) {
      target.addToBackColorGene(direction9());

      success = true;
    }

    if (mut[11] && randInt(100) < mutProb) {
      target.addToThicknessGene(direction9());
      success = true;
    }
    if (mut[0] && randInt(100) < mutProb) {
      addToSegNoGene(target, direction9());
      success = true;
    }
    IntegerGene segNoGene = target.getSegNoGene();
    if (mut[1] && segNoGene.getValue() > 1) {
      for (int j = 0; j < 8; j++)
        if (randInt(100) < mutProb / 2)
          target.setDGene(j, randSwell(target.getDGene(j)));
      if (randInt(100) < mutProb / 2)
        target.setDGene(9, randSwell(target.getDGene(9)));
      success = true;
    }
    if (mut[0] && segNoGene.getValue() > 1 && randInt(100) < mutProb) {
      addToSegDistGene(target, direction9());
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
        break;
      }
      success = true;
    }
    if (mut[4] && randInt(100) < mutProb) {
      addToTrickleGene(target, direction9());
      success = true;
    }
    if (mut[5] && randInt(100) < mutProb) {
      addToMutSizeGene(target, direction9());
      success = true;
    }
    return success;
  }

}
