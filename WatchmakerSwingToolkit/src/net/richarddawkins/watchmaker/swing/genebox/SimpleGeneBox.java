package net.richarddawkins.watchmaker.swing.genebox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.app.genebox.GeneBox;
import net.richarddawkins.watchmaker.genome.Gene;

abstract public class SimpleGeneBox extends JPanel implements PropertyChangeListener, GeneBox {
	private static final long serialVersionUID = -8872868382687102082L;

	public static int GenesHeight = 20;

	protected boolean engineeringMode = false;
	protected boolean canGooseUpDn = false;
	protected boolean canGooseLeftRight = true;
	
	protected Gene gene;

	protected JLabel valueLabel = new JLabel("X");
	
	public SimpleGeneBox(boolean engineeringMode) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		this.setLayout(new BorderLayout());
		setEngineeringMode(engineeringMode);
		add(valueLabel, BorderLayout.CENTER);
	}
	
	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.genome.gui.GeneBox#getGene()
	 */
	@Override
	public Gene getGene() {
		return gene;
	}
	
	@Override
	public void setEngineeringMode(boolean engineeringMode) {
		if(! this.engineeringMode && engineeringMode) {
			// Turn on engineering mode.
			this.addMouseMotionListener(new GeneBoxMouseMotionAdapter(this));
			this.addMouseListener(new GeneBoxMouseAdapter(this));
		} else if(this.engineeringMode && ! engineeringMode) {
			// Turn off engineering mode.
			this.removeMouseListener(this.getMouseListeners()[0]);
			this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
		}
		this.engineeringMode = engineeringMode;
	}

	@Override
	public void setGene(Gene gene) {
		if(this.gene != null) {
			this.gene.removePropertyChangeListener(this);
		}
		this.gene = gene;
		if(gene != null)
			gene.addPropertyChangeListener(this);
	}

}
