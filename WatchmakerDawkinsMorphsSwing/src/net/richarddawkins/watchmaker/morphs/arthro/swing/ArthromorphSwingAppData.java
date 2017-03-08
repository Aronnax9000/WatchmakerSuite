package net.richarddawkins.watchmaker.morphs.arthro.swing;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.arthro.ArthromorphConfig;
import net.richarddawkins.watchmaker.morphs.arthro.genebox.swing.SwingArthromorphGeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.arthro.geom.swing.SwingArthromorphPicDrawer;
import net.richarddawkins.watchmaker.morphs.arthro.menu.swing.ArthromorphMenuBuilder;
import net.richarddawkins.watchmaker.morphs.swing.MorphType;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;

public class ArthromorphSwingAppData extends SwingAppData {
	public ArthromorphSwingAppData() {
		this.setGeneBoxToSide(true);
		this.setMenuBuilder(new ArthromorphMenuBuilder(this));
		this.setPhenotypeDrawer(new SwingArthromorphPicDrawer());
		
		this.setIcon(MorphType.ARTHROMORPH.getIconFilename());
		menuBuilder.setAppData(this);
		
		this.setMorphConfig(new ArthromorphConfig());
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		GeneBoxStrip geneBoxStrip = new SwingArthromorphGeneBoxStrip(this);
		geneBoxStrip.setEngineeringMode(engineeringMode); 
		geneBoxStrip.setGeneBoxToSide(true);
		return geneBoxStrip;
	}
	
	
}
