package net.richarddawkins.watchmaker.genome.gui;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.richarddawkins.watchmaker.genome.Gene;

public class GeneBoxMouseAdapter extends MouseAdapter {
	protected SimpleGeneBox geneBox;	
	public GeneBoxMouseAdapter(SimpleGeneBox geneBox) {
		this.geneBox = geneBox;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Gene gene = geneBox.getGene();
		Cursor cursor = geneBox.getCursor();
		gene.goose(cursor);
		gene.getGenome().develop();
	}
}