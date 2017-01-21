package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.richarddawkins.watchmaker.genome.GeneManipulationAdapter;
import net.richarddawkins.watchmaker.genome.GeneManipulationEvent;
import net.richarddawkins.watchmaker.genome.GeneManipulationListener;
import net.richarddawkins.watchmaker.genome.GeneManipulationSupport;
import net.richarddawkins.watchmaker.genome.GooseDirection;
import net.richarddawkins.watchmaker.swing.images.WatchmakerCursors;
/**
 * GeneBoxMouseAdapter is the bridge between Swing mouse click events
 * (occurring on gene boxes) and GeneManipulation events. When it receives
 * a mouse click, it determines the area of the genebox clicked (left, right,
 * middle, top, bottom) and emits a GeneManipulationEvent to interested parties
 * (typically, Genes) containing the appropriate value of "GooseDirection".
 * @author sven
 *
 */
public class GeneBoxMouseAdapter extends MouseAdapter implements GeneManipulationSupport {
	protected GeneManipulationAdapter geneManipulationAdapter 
		= new GeneManipulationAdapter();
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		Cursor cursor = e.getComponent().getCursor();
		
		GooseDirection direction = null;
		
		if(cursor == WatchmakerCursors.leftArrow)
			direction = GooseDirection.leftArrow;
		else if(cursor == WatchmakerCursors.rightArrow)
			direction = GooseDirection.rightArrow;
		else if (cursor == WatchmakerCursors.upArrow)
			direction = GooseDirection.upArrow;
		else if (cursor == WatchmakerCursors.downArrow)
			direction = GooseDirection.downArrow;
		else if (cursor == WatchmakerCursors.equalsSign)
			direction = GooseDirection.equalsSign;
		if(direction != null)
			fireGeneManipulationEvent(new GeneManipulationEvent(direction));
		
	}

	@Override
	public void fireGeneManipulationEvent(GeneManipulationEvent event) {
		geneManipulationAdapter.fireGeneManipulationEvent(event);
	}

	@Override
	public void addGeneManipulationListener(GeneManipulationListener listener) {
		geneManipulationAdapter.addGeneManipulationListener(listener);
		
	}

	@Override
	public void removeAllGeneManipulationListeners() {
		geneManipulationAdapter.removeAllGeneManipulationListeners();
		
	}
	
	
	@Override
	public void removeGeneManipulationListener(GeneManipulationListener listener) {
		geneManipulationAdapter.removeGeneManipulationListener(listener);
		
	}

	@Override
	public GeneManipulationListener[] getGeneManipulationListeners() {
		return geneManipulationAdapter.getGeneManipulationListeners();
	}
}