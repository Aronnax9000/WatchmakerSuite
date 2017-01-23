package net.richarddawkins.watchmaker.morphs.colour.swing;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.colour.ColourBiomorphConfig;
import net.richarddawkins.watchmaker.morphs.colour.genome.swing.SwingColorGeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.colour.geom.swing.SwingColourPicDrawer;
import net.richarddawkins.watchmaker.morphs.swing.MorphType;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;

public class ColourSwingAppData extends SwingAppData {
	
	public ColourSwingAppData() {
		this.setIcon(MorphType.COLOUR_BIOMORPH.getIconFilename());
		
		setMenuBuilder(new ColourMenuBuilder(this));
		setPhenotypeDrawer(new SwingColourPicDrawer());
		config = new ColourBiomorphConfig();
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) { 
		GeneBoxStrip geneBoxStrip = new SwingColorGeneBoxStrip();
		geneBoxStrip.setEngineeringMode(engineeringMode);
		return geneBoxStrip;
	}

}
