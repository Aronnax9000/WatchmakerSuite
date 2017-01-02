package net.richarddawkins.watchmaker.morphs;

public abstract class SimpleMutagenImpl implements Mutagen {
  protected Person genome;

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.Mutagen#setPerson(net.richarddawkins.watchmaker.
   * morphs.Person)
   */
  @Override
  public void setPerson(Person genome) {
    this.genome = genome;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.impl.Mutagen#getGenome()
   */
  @Override
  public Person getGenome() {
    return genome;
  }
}
