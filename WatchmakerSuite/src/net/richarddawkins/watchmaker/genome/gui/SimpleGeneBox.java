package net.richarddawkins.watchmaker.genome.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genome.Gene;

abstract public class SimpleGeneBox extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = -8872868382687102082L;

	public static int GenesHeight = 20;

	protected boolean engineeringMode = false;
	protected boolean canGooseUpDn = false;
	protected boolean canGooseLeftRight = true;
	
	protected Gene gene;


	protected int geneBoxNo;


	protected GeneBoxStrip geneBoxStrip;
	protected JLabel valueLabel = new JLabel("X");
	
	public SimpleGeneBox(GeneBoxStrip geneBoxStrip, boolean engineeringMode) {
		setEngineeringMode(engineeringMode);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setLayout(new BorderLayout());
		this.geneBoxStrip = geneBoxStrip;
		
		// I actually think the extra valuePanel isn't needed:
		// The valueLabel could probably be added directly to the present panel. - ABC
		JPanel valuePanel = new JPanel();
		this.add(valuePanel, BorderLayout.CENTER);
		valuePanel.add(valueLabel);

	}
	
	public Gene getGene() {
		return gene;
	}


	public GeneBoxStrip getGeneBoxStrip() {
		return geneBoxStrip;
	}

	
	public void setEngineeringMode(boolean engineeringMode) {
		if(! this.engineeringMode && engineeringMode) {
			if (this.getGeneBoxStrip().engineeringMode) {
				this.addMouseMotionListener(new GeneBoxMouseMotionAdapter(this));
				this.addMouseListener(new GeneBoxMouseAdapter(this));
			}
		} else if(this.engineeringMode && ! engineeringMode) {
			this.removeMouseListener(this.getMouseListeners()[0]);
			this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
		}
		this.engineeringMode = engineeringMode;

		
	}

	public void setGene(Gene gene) {
		if(this.gene != null) {
			this.gene.removePropertyChangeListener(this);
		}
		this.gene = gene;
		if(gene != null)
			gene.addPropertyChangeListener(this);
	}

	public void setGeneBoxStrip(GeneBoxStrip geneBoxStrip) {
		this.geneBoxStrip = geneBoxStrip;
	}

	public void goose(Cursor cursor) {
		
		
	}


}
