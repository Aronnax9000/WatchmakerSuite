package net.richarddawkins.watchmaker.morph.colour;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import net.richarddawkins.watchmaker.genome.gui.IntegerGeneBox;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.mono.gui.MonochromeGeneBoxStrip;

public class ColourGeneBoxStrip extends MonochromeGeneBoxStrip {

	private static final long serialVersionUID = 1L;

	public ColourGeneBoxStrip(MorphConfig config, boolean engineeringMode) {
	    super(config, engineeringMode);
	    GridBagConstraints constraints = ((GridBagLayout)this.getLayout())
	    	.getConstraints(this.getComponent(this.getComponentCount() - 1));
	    
	    constraints.gridx++;
		this.add(new ColorGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new ColorGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new ColorGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new ColorGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new ColorGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new ColorGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new ColorGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new ColorGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new ColorGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new LimbShapeGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new LimbFillGeneBox(this, engineeringMode), constraints);
	    constraints.gridx++;
		this.add(new IntegerGeneBox(this, engineeringMode), constraints);

	    

	  }
		

}
