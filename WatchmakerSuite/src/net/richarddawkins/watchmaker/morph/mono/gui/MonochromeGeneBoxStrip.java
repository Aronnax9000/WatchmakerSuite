package net.richarddawkins.watchmaker.morph.mono.gui;

import java.awt.GridBagConstraints;

import net.richarddawkins.watchmaker.genome.gui.IntegerGeneBox;
import net.richarddawkins.watchmaker.genome.gui.SimpleGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.biomorph.genome.gui.CompletenessGeneBox;
import net.richarddawkins.watchmaker.morph.biomorph.genome.gui.IntegerGradientGeneBox;
import net.richarddawkins.watchmaker.morph.biomorph.genome.gui.SpokesGeneBox;

public class MonochromeGeneBoxStrip extends SimpleGeneBoxStrip {

	private static final long serialVersionUID = 1L;

	
	public MonochromeGeneBoxStrip(MorphConfig config, boolean engineeringMode) {
		super(config, engineeringMode);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1;
		constraints.gridy = 0;
		
		constraints.gridx = 0;		
		this.add(new IntegerGradientGeneBox(this, engineeringMode));
		
		constraints.gridx++;
		this.add(new IntegerGradientGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGradientGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGradientGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGradientGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGradientGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGradientGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGradientGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGradientGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGradientGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new CompletenessGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new SpokesGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGeneBox(this, engineeringMode), constraints);
		constraints.gridx++;
		this.add(new IntegerGeneBox(this, engineeringMode), constraints);
	}
}
