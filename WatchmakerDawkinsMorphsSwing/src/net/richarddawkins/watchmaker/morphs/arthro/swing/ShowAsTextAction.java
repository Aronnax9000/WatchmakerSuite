package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.swing.app.SwingAppData;
import net.richarddawkins.watchmaker.swing.menu.SwingWatchmakerAction;

public class ShowAsTextAction extends SwingWatchmakerAction {
	protected SwingAppData swingAppData;
	// protected ArthromorphConfig config;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame = null;

	public ShowAsTextAction(AppData appData) {
		super("Show as Text");
		this.appData = appData;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		/*
		 * BreedingPanel panel =
		 * config.getBreedingAndGeneBoxPanel().getBreedingPanel(); Arthromorph
		 * morph = (Arthromorph)
		 * panel.getMorphPanels().elementAt(panel.getMidBox()) .getMorph();
		 * ArthromorphPerson genome = (ArthromorphPerson) morph.getGenome();
		 * AtomPrinter.printMiddle(genome.getAnimalTrunk());
		 */
	}

}
