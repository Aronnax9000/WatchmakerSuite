package net.richarddawkins.watchmaker.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;

public class ActionEngineering extends AbstractAction {


	private static final long serialVersionUID = 4121419685469500509L;
	MorphConfig config;
	
	public ActionEngineering(MorphConfig config, String name, Icon icon) {
		super(name, icon);
		this.config = config;
	}
	public ActionEngineering(MorphConfig config) {
		this(config, "Engineering", null);
		this.config = config;

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    Morph morph = config.getMorphOfTheHour();
		config.addEngineeringMorphView(morph);
	}

}
