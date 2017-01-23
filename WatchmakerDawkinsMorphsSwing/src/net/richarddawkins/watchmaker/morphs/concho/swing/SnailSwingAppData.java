package net.richarddawkins.watchmaker.morphs.concho.swing;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.concho.SnailConfig;
import net.richarddawkins.watchmaker.morphs.swing.MorphType;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;

public class SnailSwingAppData extends SwingAppData {
	
	public SnailSwingAppData() {
		this.setIcon(MorphType.SNAIL.getIconFilename());
		
		setMenuBuilder(new SnailMenuBuilder(this));
		setPhenotypeDrawer(new SwingSnailPicDrawer());
		config = new SnailConfig();
		
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		GeneBoxStrip geneBoxStrip = new SwingSnailGeneBoxStrip();
		geneBoxStrip.setEngineeringMode(engineeringMode);
		return geneBoxStrip;
	}
}
