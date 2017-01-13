package net.richarddawkins.watchmaker.genome.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GeneBoxMouseAdapter extends MouseAdapter {
	protected SimpleGeneBox geneBox;	
	public GeneBoxMouseAdapter(SimpleGeneBox geneBox) {
		this.geneBox = geneBox;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		geneBox.goose(geneBox.getCursor());
	}
}