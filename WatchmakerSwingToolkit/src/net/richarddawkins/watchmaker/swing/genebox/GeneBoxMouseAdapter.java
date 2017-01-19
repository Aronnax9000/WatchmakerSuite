package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;

public class GeneBoxMouseAdapter extends MouseAdapter {
	protected SimpleGeneBox geneBox;	
	public GeneBoxMouseAdapter(SimpleGeneBox geneBox) {
		this.geneBox = geneBox;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Gene gene = geneBox.getGene();
		Cursor cursor = geneBox.getCursor();
		GooseDirection direction = null;
		if(cursor == WatchmakerCursors.leftArrow)
			direction = GooseDirection.leftArrow;
		else if(cursor == WatchmakerCursors.rightArrow)
			direction = GooseDirection.rightArrow;
		else if (cursor == WatchmakerCursors.upArrow)
			direction = GooseDirection.upArrow;
		else if (cursor == WatchmakerCursors.equalsSign)
			direction = GooseDirection.equalsSign;
		if(direction != null)
			gene.goose(direction);
		gene.getGenome().develop();
	}
}