package net.richarddawkins.watchmaker.genome;

import java.util.EventListener;

public interface GenomeChangeListener extends EventListener {

	public void genomeChange(GenomeChangeEvent evt);
	
}
