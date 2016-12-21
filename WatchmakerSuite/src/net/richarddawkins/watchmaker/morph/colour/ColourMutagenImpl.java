package net.richarddawkins.watchmaker.morph.colour;

import static net.richarddawkins.watchmaker.morph.util.Random.randInt;

import net.richarddawkins.watchmaker.morph.common.BiomorphGenome;
import net.richarddawkins.watchmaker.morph.common.BiomorphMutagenImpl;
import net.richarddawkins.watchmaker.morph.common.Genome;
import net.richarddawkins.watchmaker.morph.mono.CompletenessType;
import net.richarddawkins.watchmaker.morph.mono.SpokesType;

public class ColourMutagenImpl extends BiomorphMutagenImpl {
  ColourBiomorphConfig config;
  public ColourMutagenImpl(ColourBiomorphConfig config) {
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
    ColourPerson target = (ColourPerson) genome;
    boolean success = false;
    boolean[] mut = config.getMut();
    if (mut[6] && randInt(100) < target.getMutProbGene()) {
      target.addToMutProbGene(direction9());
      success = true;
    }
    int mutProbGene = target.getMutProbGene();
    if (mut[12]) {
      for (int j = 0; j < 8; j++)
        if (randInt(100) < mutProbGene) {
          target.addToGene(j, direction((BiomorphGenome)genome));
          success = true;
        }
      if (randInt(100) < mutProbGene) {
        target.addToGene(8, direction9());
        success = true;
      }

    }
    if (mut[9])
      for (int j = 0; j < 8; j++)
        if (randInt(100) < mutProbGene) {
          target.addToColorGene(j, direction9());
          success = true;
        }
    if (mut[7] && randInt(100) < mutProbGene) {
      target.setLimbShapeGene(randLimbType());
      success = true;
    }
    if (mut[8] && randInt(100) < mutProbGene) {
      target.setLimbFillGene(randLimbFill());
      success = true;
    }

    if (mut[10] && randInt(100) < mutProbGene) {
      target.addToBackColorGene(direction9());

      success = true;
    }

    if (mut[11] && randInt(100) < mutProbGene) {
      target.addToThicknessGene(direction9());
      success = true;
    }
    if (mut[0] && randInt(100) < mutProbGene) {
      target.addToSegNoGene(direction9());
      success = true;
    }
    if (mut[1] && target.getSegNoGene() > 1) {
      for (int j = 0; j < 8; j++)
        if (randInt(100) < mutProbGene / 2)
          target.setDGene(j, randSwell(target.getDGene(j)));
      if (randInt(100) < mutProbGene / 2)
        target.setDGene(9, randSwell(target.getDGene(9)));
      success = true;
    }
    if (mut[0] && target.getSegNoGene() > 1 && randInt(100) < mutProbGene) {
      target.addToSegDistGene(direction9());
      success = true;
    }
    if (mut[2] && randInt(100) < mutProbGene / 2) {
      target.setCompletenessGene(target.getCompletenessGene() == CompletenessType.Single
          ? CompletenessType.Double : CompletenessType.Single);
      success = true;
    }
    if (mut[3] && randInt(100) < mutProbGene / 2) {
      switch (target.getSpokesGene()) {
      case NorthOnly:
        target.setSpokesGene(SpokesType.NSouth);
        break;
      case NSouth:
        target.setSpokesGene(direction9() == 1 ? SpokesType.Radial : SpokesType.NorthOnly);
        break;
      case Radial:
        target.setSpokesGene(SpokesType.NSouth);
        break;
      }
      success = true;
    }
    if (mut[4] && randInt(100) < mutProbGene) {
      target.addToTrickleGene(direction9());
      success = true;
    }
    if (mut[5] && randInt(100) < mutProbGene) {
      target.addToMutSizeGene(direction9());
      success = true;
    }
    return success;
  }

}
