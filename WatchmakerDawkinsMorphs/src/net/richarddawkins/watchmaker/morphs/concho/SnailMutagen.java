package net.richarddawkins.watchmaker.morphs.concho;

import static net.richarddawkins.watchmaker.util.Random.randInt;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.Mutagen;

public class SnailMutagen implements Mutagen {
  protected SnailConfig config;
  public SnailMutagen(SnailConfig config) {
    this.config = (SnailConfig) config;
  }

  public void setMorphConfig(MorphConfig config) {
    this.config = (SnailConfig) config;
  }
  
  public MorphConfig getMorphConfig() {
    return config;
  }
  
  
  public double direction() {
    if (randInt(2) == 2) {
      return 1;
    } else {
      return -1;
    }
  }

  /**
   * we want to change by large amounts when low, small amounts when large
   * 
   * @return
   */
  private double margarine(double w, double direction) {
    double wMutSize = 0.1f;
    double logged = Math.log(w);
    double logchanged = logged + wMutSize * direction();
    if (logchanged > 20.0f) {
      logchanged = 20.0f;
    }
    double m = Math.exp(logchanged);
    if (m < 1.0f) {
      m = 1.0f;
    }
    return m;
  }

  private double direction9() {
    int r = randInt(5);
    switch (r) {
    case 5:
      return 2;
    case 4:
      return 1;
    case 3:
      return 0;
    case 2:
      return -1;
    case 1:
      return -2;
    default:
      return 0;
    }
  }

  public static final double DMUTSIZE = 0.01d;
  public static final double SMUTSIZE = 0.1d;
  public static final double TMUTSIZE = 0.1d;

  public boolean mutate(Genome genome) {
    SnailGenome target = (SnailGenome) genome;
    int mutProb = target.getMutProb();
    boolean success = false;
    if (randInt(100) < mutProb) {
      target.setwOpening(margarine(target.getwOpening(), direction()));
      success = true;
    }

    if (randInt(100) < mutProb) {
      target.addToDDisplacement(direction9() * DMUTSIZE);
      success = true;
    }
    if (randInt(100) < mutProb && config.isSideView()) {
      // Don't let Translation gene drift when you can't see its consequences
      target.addToTTranslation(direction9() * TMUTSIZE);
      success = true;
    }
    if (randInt(100) < 1) {
      target.flipHandedness();

      success = true;
    }
    return success;
  }

}
