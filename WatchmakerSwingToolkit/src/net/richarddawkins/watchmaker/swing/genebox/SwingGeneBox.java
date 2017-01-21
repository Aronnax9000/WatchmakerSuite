package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;

abstract public class SwingGeneBox extends JPanel implements GeneBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JLabel valueLabel = new JLabel("X");
	
	public void setText(String newValue) {
		valueLabel.setText(newValue);
		repaint();
	}
	
	/**
	 * Creates a GeneBoxMouseMotionAdapter and adds it to the JPanel's mouse
	 * motion listener collection. The GeneBoxMouseMotionAdapter changes
	 * the cursor to the correct icon depending on the type of the gene box
	 * and the position of the mouse. It emits no events of its own.
	 * 
	 * Creates a GeneBoxMouseAdapter and adds it to the JPanel's mouse listener
	 * collection. The GeneBoxMouseAdapter emits GeneManipulationEvents to interested
	 * parties in response to mouse click events. The gene is added to the
	 * mouse adapter's GeneManipulationListener collection, so that GeneManipulation
	 * Events will be routed to the interested gene.
	 * @param gene
	 * @param geneBoxType
	 */
	public SwingGeneBox() {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setLayout(new BorderLayout());
		add(valueLabel, BorderLayout.CENTER);
	}
	
	public void setEngineeringMode(GeneBoxType geneBoxType) {
		GeneBoxMouseMotionAdapter mouseMotionAdapter = new GeneBoxMouseMotionAdapter(geneBoxType);
		this.addMouseMotionListener(mouseMotionAdapter);
		geneBoxMouseAdapter = new GeneBoxMouseAdapter();
		this.addMouseListener(geneBoxMouseAdapter);
	}

	protected Gene gene;
	protected GeneBoxMouseAdapter geneBoxMouseAdapter = null; 

	@Override
	public Gene getGene() {
		return gene;
	}


	@Override
	public void setGene(Gene newValue) {
		Gene oldValue = this.gene;
		if(oldValue != null) {
			oldValue.removePropertyChangeListener(this);
			geneBoxMouseAdapter.removeGeneManipulationListener(oldValue);
		}
		gene = newValue;
		if(gene != null) {
			// Tell the Gene to update us if its value changes.
			gene.addPropertyChangeListener(this);
			// Tell the mouse adapter to inform the gene of manipulations.
			geneBoxMouseAdapter.addGeneManipulationListener(gene);
		}
	}
}
