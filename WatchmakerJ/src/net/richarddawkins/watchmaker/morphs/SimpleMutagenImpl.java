package net.richarddawkins.watchmaker.morphs;

public abstract class SimpleMutagenImpl implements Mutagen {
  protected Genome genome;

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.Mutagen#setPerson(net.richarddawkins.watchmaker.
   * morphs.Person)
   */
  @Override
  public void setPerson(Genome genome) {
    this.genome = genome;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.Mutagen#getGenome()
   */
  @Override
  public Genome getGenome() {
    return genome;
  }
}
