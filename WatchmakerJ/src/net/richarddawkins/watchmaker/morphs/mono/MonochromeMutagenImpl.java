package net.richarddawkins.watchmaker.morphs.mono;

import static net.richarddawkins.watchmaker.Random.randInt;

import net.richarddawkins.watchmaker.morphs.Person;
import net.richarddawkins.watchmaker.morphs.biomorph.BiomorphMutagenImpl;
import net.richarddawkins.watchmaker.morphs.biomorph.CompletenessType;
import net.richarddawkins.watchmaker.morphs.biomorph.SpokesType;

public class MonochromeMutagenImpl extends BiomorphMutagenImpl {

  public MonochromeMutagenImpl(Person genome) {
    this.genome = genome;
  }

  public boolean mutate() {
    MonochromePerson target = (MonochromePerson) genome;
    boolean success = false;
    boolean[] mut = target.getMorph().getMorphConfig().getMut();
    if (mut[6]) {
      if (randInt(100) < target.getMutProbGene()) {
        target.addToMutProbGene(direction9());
        success = true;
      }
    }
    for (int j = 0; j < 8; j++) {
      if (randInt(100) < target.getMutProbGene()) {
        target.addToGene(j, direction());
        success = true;
      }
    }
    if (randInt(100) < target.getMutProbGene()) {
      target.addToGene(8, direction9());
      success = true;
    }

    if (mut[0] && randInt(100) < target.getMutProbGene()) {
      int j = direction9();
      target.addToSegNoGene(j);
      success = true;
    }
    if (mut[1] && target.getSegNoGene() > 1) {
      for (int j = 0; j < 7; j++)
        if (randInt(100) < target.getMutProbGene() / 2) {
          target.setDGene(j, randSwell(target.getDGene(j)));
          success = true;
        }
      if (randInt(100) < target.getMutProbGene() / 2) {
        target.setDGene(9, randSwell(target.getDGene(9)));
        success = true;
      }
    }
    if (mut[7] && mut[8] && randInt(100) < target.getMutProbGene()) {
      target.setDGene(8, randSwell(target.getDGene(8)));
      success = true;
    }
    if (mut[0] && target.getSegNoGene() > 1 && randInt(100) < target.getMutProbGene()) {
      target.setSegDistGene(direction9());
      success = true;
    }
    if (mut[2] && randInt(100) < target.getMutProbGene() / 2) {
      target.setCompletenessGene(target.getCompletenessGene() == CompletenessType.Single
          ? CompletenessType.Double : CompletenessType.Single);
      success = true;
    }
    if (mut[3] && randInt(100) < target.getMutProbGene() / 2) {
      switch (target.getSpokesGene()) {
      case NorthOnly:
        target.setSpokesGene(SpokesType.NSouth);
        break;
      case NSouth:
        target.setSpokesGene(direction9() == 1 ? SpokesType.Radial : SpokesType.NorthOnly);
        break;
      case Radial:
        target.setSpokesGene(SpokesType.NSouth);
      }
      success = true;
    }
    if (mut[4] && randInt(100) < Math.abs(target.getMutProbGene())) {
      target.addToTrickleGene(direction9());
      success = true;
    }
    if (mut[5] && randInt(100) < Math.abs(target.getMutProbGene())) {
      target.addToMutSizeGene(direction9());
      success = true;
    }
    return success;
  }

}
