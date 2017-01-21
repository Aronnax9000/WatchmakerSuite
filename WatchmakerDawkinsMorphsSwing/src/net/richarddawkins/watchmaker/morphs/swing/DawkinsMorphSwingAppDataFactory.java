package net.richarddawkins.watchmaker.morphs.swing;

import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import dictionary.spi.Dictionary;
import net.richarddawkins.watchmaker.morphs.arthro.swing.ArthromorphSwingAppData;
import net.richarddawkins.watchmaker.morphs.colour.swing.ColourSwingAppData;
import net.richarddawkins.watchmaker.morphs.concho.swing.SnailSwingAppData;
import net.richarddawkins.watchmaker.morphs.mono.swing.MonochromeSwingAppData;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppData;
import net.richarddawkins.watchmaker.swing.appdata.SwingAppDataFactory;
import net.richarddawkins.watchmaker.swing.images.ClassicImageLoader;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewsTabbedPanel;

public class DawkinsMorphSwingAppDataFactory implements SwingAppDataFactory , Dictionary {
   

	@Override
	public Vector<String> getMorphTypes() {
		Vector<String> vec = new Vector<String>();
		for(MorphType morphType: MorphType.values())
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

	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.morphs.swing.SwingAppDataFactory#getMorphType()
	 */
	@Override
	public String getMorphType() {
		return morphType;
	}

	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.morphs.swing.SwingAppDataFactory#setMorphType(net.richarddawkins.watchmaker.morph.MorphType)
	 */
	@Override
	public void setMorphType(String name) {
		morphType = name;
		
		for(MorphType morphEnum: MorphType.values())
			if(name.equals(morphEnum.getName())) {
				this.icon = new ImageIcon(ClassicImageLoader.getPicture(name).getImage());
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

	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.morphs.swing.SwingAppDataFactory#newSwingAppData()
	 */
	@Override
	public SwingAppData newSwingAppData() {
		SwingAppData swingAppData;
		
		switch (morphType) {
		case "Arthromorphs":
			swingAppData = new ArthromorphSwingAppData();
			break;
		case "Colour":
			swingAppData = new ColourSwingAppData();
			break;
		case "Snails":
			swingAppData = new SnailSwingAppData();
			break;
		case "Monochrome":
			swingAppData = new MonochromeSwingAppData();
			break;
		default:
			swingAppData = null;

		}
		
		
		swingAppData.setMorphViewsTabbedPane(new SwingMorphViewsTabbedPanel(swingAppData));

		return swingAppData;
	}


	@Override
	public String getDefinition(String word) {
		
		return "Hi From Dawkins";
	}





}
