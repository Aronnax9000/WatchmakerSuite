package net.richarddawkins.watchmaker.morphs.concho.app.swing;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morphs.concho.SnailConfig;
import net.richarddawkins.watchmaker.morphs.concho.genebox.swing.SwingSnailGeneBoxStrip;
import net.richarddawkins.watchmaker.morphs.concho.geom.swing.SwingSnailPicDrawer;
import net.richarddawkins.watchmaker.morphs.concho.menu.swing.SnailMenuBuilder;
import net.richarddawkins.watchmaker.morphs.swing.MorphType;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;

public class SnailSwingAppData extends SwingAppData {
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.concho.app.swing.SnailSwingAppData");
	public SnailSwingAppData() {
		this.setIcon(MorphType.SNAIL.getIconFilename());
		
		setMenuBuilder(new SnailMenuBuilder(this));
		setPhenotypeDrawer(new SwingSnailPicDrawer());
		this.setMorphConfig(new SnailConfig());
		
	}
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {
		GeneBoxStrip geneBoxStrip = new SwingSnailGeneBoxStrip();
		geneBoxStrip.setEngineeringMode(engineeringMode);
		geneBoxStrip.setGeneBoxToSide(false);
		return geneBoxStrip;
	}
	
	@Override
	public void addDefaultMorphView() {
		Morph morph = config.newMorph(1);
		logger.info(morph.getGenome().toString());
		this.addEngineeringMorphView(morph);
	}
}
