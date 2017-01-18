package net.richarddawkins.watchmaker.morphs.arthro.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;

public class ShowAsTextAction extends AbstractAction {
	protected SwingAppData swingAppData;
	// protected ArthromorphConfig config;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JFrame frame = null;

	public ShowAsTextAction(SwingAppData swingAppData) {
		super("Show as Text");
		this.swingAppData = swingAppData;
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
