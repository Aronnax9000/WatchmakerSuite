package net.richarddawkins.watchmaker.morphs.swing;

import java.util.Vector;

import javax.swing.Icon;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.app.AppDataFactory;
import net.richarddawkins.watchmaker.morphs.arthro.swing.ArthromorphSwingAppData;
import net.richarddawkins.watchmaker.morphs.colour.swing.ColourSwingAppData;
import net.richarddawkins.watchmaker.morphs.concho.app.swing.SnailSwingAppData;
import net.richarddawkins.watchmaker.morphs.mono.app.swing.MonochromeSwingAppData;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewsTabbedPanel;

public class DawkinsMorphSwingAppDataFactory implements AppDataFactory {

	@Override
	public Vector<String> getMorphTypes() {
		Vector<String> vec = new Vector<String>();
		for (MorphType morphType : MorphType.values())
			vec.add(morphType.getName());
		return vec;
	}

	protected String morphType;
	protected String toolTip;

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.morphs.swing.SwingAppDataFactory#
	 * getMorphType()
	 */
	@Override
	public String getMorphType() {
		return morphType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.richarddawkins.watchmaker.morphs.swing.SwingAppDataFactory#
	 * setMorphType(net.richarddawkins.watchmaker.morph.MorphType)
	 */
	@Override
	public void setMorphType(String name) {
		morphType = name;

		for (MorphType morphEnum : MorphType.values())
			if (name.equals(morphEnum.getName())) {
				this.icon = morphEnum.getIcon();
				this.toolTip = morphEnum.getToolTip();
				break;
			}

	}

	protected String name;
	protected Icon icon;

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DawkinsMorphSwingAppDataFactory() {
		this.name = "Dawkins' Morphs";
	};


	/**
	 * @return a new SwingAppData depending on the value of MorphType (Arthromorphs, Colour, Snails, Monochrome)
	 */
	@Override
	public AppData newAppData() {
		AppData swingAppData = null;
		if(morphType.equals("Arthromorphs")) {
			swingAppData = new ArthromorphSwingAppData();
		} else if(morphType.equals("Colour")) {
			swingAppData = new ColourSwingAppData();
		} else if(morphType.equals("Snails")) {
			swingAppData = new SnailSwingAppData();
		} else if(morphType.equals("Monochrome")) {
			swingAppData = new MonochromeSwingAppData();
		} 
		if(swingAppData != null) {
			swingAppData.setName(morphType);
			swingAppData.setMorphViewsTabbedPane(new SwingMorphViewsTabbedPanel(swingAppData));
		}
		return swingAppData;
	}


}
