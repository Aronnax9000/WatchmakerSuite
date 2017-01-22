package net.richarddawkins.watchmaker.morph;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.phenotype.Phenotype;

public interface Morph {
	
	public void setMorphConfig(MorphConfig config);
	public MorphConfig getMorphConfig();

	public void setGenome(Genome genome);
	public Genome getGenome();

	public void setPic(Phenotype pic);
	public Phenotype getPic();

	public Morph reproduce();
    public MorphPedigree getPedigree();
    
    public Object getImage();
    public void setImage(Object object);

}