package net.richarddawkins.watchmaker.morphs.bio.genebox;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;

public interface CompletenessGeneBox extends GeneBox  {
	public void setCompleteness(CompletenessType completenessType);

}
