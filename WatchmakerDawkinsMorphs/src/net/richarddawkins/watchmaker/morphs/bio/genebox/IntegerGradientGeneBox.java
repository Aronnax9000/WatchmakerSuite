package net.richarddawkins.watchmaker.morphs.bio.genebox;

import net.richarddawkins.watchmaker.genebox.IntegerGeneBox;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;

public interface IntegerGradientGeneBox extends IntegerGeneBox {

	public void setGradient(SwellType swellType);
	
}
