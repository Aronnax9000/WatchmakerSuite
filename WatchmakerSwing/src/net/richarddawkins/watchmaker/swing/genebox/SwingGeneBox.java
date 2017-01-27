package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;

abstract public class SwingGeneBox extends JPanel implements GeneBox {

	protected JLabel valueLabel = new JLabel("X");
	
	public void setText(String newValue) {
		valueLabel.setText(newValue);
		repaint();
	}
	
	public SwingGeneBox() {
		valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setLayout(new BorderLayout());
		
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
		GeneBoxMouseMotionAdapter mouseMotionAdapter = new GeneBoxMouseMotionAdapter(geneBoxType);
		this.addMouseMotionListener(mouseMotionAdapter);
		geneBoxMouseAdapter = new GeneBoxMouseAdapter();
		this.addMouseListener(geneBoxMouseAdapter);
	}
}
