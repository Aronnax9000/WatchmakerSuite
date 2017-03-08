package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;

abstract public class SwingGeneBox extends JPanel implements GeneBox {

	protected JLabel valueLabel = new JLabel("X");
	
	public void setText(String newValue) {
		valueLabel.setText(newValue);
		repaint();
	}
	protected AppData appData;
	public SwingGeneBox(AppData appData) {
	    this.appData = appData;
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setLayout(new BorderLayout());
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
	}
	
	private static final long serialVersionUID = 1L;
	protected Gene gene;
	protected GeneBoxMouseAdapter geneBoxMouseAdapter = null; 
	@Override
	public Gene getGene() {
		return gene;
	}

	@Override
	public void setGene(Gene newValue) {
		Gene oldValue = this.gene;
		gene = newValue;
		if(geneBoxMouseAdapter != null) {
			if(oldValue != null) {
				oldValue.removePropertyChangeListener(this);
				geneBoxMouseAdapter.removeGeneManipulationListener(oldValue);
			}
			if(gene != null) {
				// Tell the Gene to update us if its value changes.
				gene.addPropertyChangeListener(this);
				// Tell the mouse adapter to inform the gene of manipulations.
				geneBoxMouseAdapter.addGeneManipulationListener(gene);
			}
		}
	}
	
	public void setEngineeringMode(GeneBoxType geneBoxType) {
        geneBoxMouseAdapter = new GeneBoxMouseAdapter(geneBoxType, appData.getWatchmakerCursorFactory());
		this.addMouseMotionListener(geneBoxMouseAdapter);
		this.addMouseListener(geneBoxMouseAdapter);
	}
}
