package net.richarddawkins.watchmaker.morphs.colour.genome.mutation;

import static net.richarddawkins.watchmaker.genome.mutation.Random.randInt;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.mutation.AllowedMutations;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morphs.bio.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.SpokesGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.mutation.BiomorphMutagen;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColorGene;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColourGenome;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbFillType;
import net.richarddawkins.watchmaker.morphs.colour.genome.type.LimbShapeType;

public class ColourMutagen extends BiomorphMutagen {


  public ColourMutagen(AllowedMutations allowedMutations) {
		super(allowedMutations);
		
	}

LimbShapeType randLimbType() {
    switch (randInt(3)) {
    case 1:
      return LimbShapeType.Stick;
    case 2:
      return LimbShapeType.Oval;
    case 3:
      return LimbShapeType.Rectangle;
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
    ColourAllowedMutations colourAllowedMutations = (ColourAllowedMutations) allowedMutations;
    boolean success = false;
    boolean[] mut = colourAllowedMutations.getMut();
    int mutProb = target.getMutProbGene().getValue();
    if (mut[6] && randInt(100) < mutProb) {
      target.getMutProbGene().addToGene(direction9());
      // Update local copy of MutProbGene
      mutProb = target.getMutProbGene().getValue();
      success = true;
    }
    if (mut[12]) {
      for (int j = 0; j < 8; j++)
        if (randInt(100) < mutProb) {
        	((IntegerGene)target.getGene(j)).addToGene(direction((BiomorphGenome)genome));
          success = true;
        }
      if (randInt(100) < mutProb) {
    	  ((IntegerGene)target.getGene(8)).addToGene(direction9());
        success = true;
      }

    }
    ColorGene[] colorGenes = target.getColorGenes();
    if (mut[9])
      for (int j = 0; j < 8; j++)
        if (randInt(100) < mutProb) {
          colorGenes[j].addToGene(direction9());
          success = true;
        }
    if (mut[7] && randInt(100) < mutProb) {
      target.getLimbShapeGene().setValue(randLimbType());
      success = true;
    }
    if (mut[8] && randInt(100) < mutProb) {
      target.getLimbFillGene().setValue(randLimbFill());
      success = true;
    }

    if (mut[10] && randInt(100) < mutProb) {
      target.addToBackColorGene(direction9());

      success = true;
    }

    if (mut[11] && randInt(100) < mutProb) {
      target.getThicknessGene().addToGene(direction9());
      success = true;
    }
    if (mut[0] && randInt(100) < mutProb) {
      target.getSegNoGene().addToGene(direction9());
      success = true;
    }
    IntegerGene segNoGene = target.getSegNoGene();
    if (mut[1] && segNoGene.getValue() > 1) {
      for (int j = 0; j < 8; j++)
        if (randInt(100) < mutProb / 2) {
        	IntegerGradientGene gradGene = (IntegerGradientGene)target.getGene(j);
        	gradGene.setGradient(randSwell(gradGene.getGradient()));
        }
      if (randInt(100) < mutProb / 2) {
    	  IntegerGradientGene gradGene = target.getSegDistGene(); 
      	gradGene.setGradient(randSwell(gradGene.getGradient()));
    	  
      }
        
      success = true;
    }
    if (mut[0] && segNoGene.getValue() > 1 && randInt(100) < mutProb) {
      target.getSegDistGene().addToGene(direction9());
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
      target.getTrickleGene().addToGene(direction9());
      success = true;
    }
    if (mut[5] && randInt(100) < mutProb) {
      target.getMutSizeGene().addToGene(direction9());
      success = true;
    }
    return success;
  }

}
