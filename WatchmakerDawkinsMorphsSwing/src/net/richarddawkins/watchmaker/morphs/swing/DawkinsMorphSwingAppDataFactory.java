package net.richarddawkins.watchmaker.morphs.swing;

import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.geom.swing.SwingPicDrawer;
import net.richarddawkins.watchmaker.gui.MorphViewsTabbedPane;
import net.richarddawkins.watchmaker.gui.SwingAppData;
import net.richarddawkins.watchmaker.gui.SwingAppDataFactory;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.MorphTypeNotSupportedException;
import net.richarddawkins.watchmaker.morphs.MorphConfigFactory;
import net.richarddawkins.watchmaker.morphs.MorphType;
import net.richarddawkins.watchmaker.morphs.arthro.geom.gui.swing.SwingArthroPicDrawer;
import net.richarddawkins.watchmaker.morphs.arthro.swing.ArthromorphMenuBuilder;
import net.richarddawkins.watchmaker.morphs.arthro.swing.ArthromorphSwingAppData;
import net.richarddawkins.watchmaker.morphs.colour.geom.swing.SwingColourPicDrawer;
import net.richarddawkins.watchmaker.morphs.colour.swing.ColourMenuBuilder;
import net.richarddawkins.watchmaker.morphs.colour.swing.ColourSwingAppData;
import net.richarddawkins.watchmaker.morphs.concho.swing.SnailMenuBuilder;
import net.richarddawkins.watchmaker.morphs.concho.swing.SnailSwingAppData;
import net.richarddawkins.watchmaker.morphs.concho.swing.SwingSnailPicDrawer;
import net.richarddawkins.watchmaker.morphs.mono.geom.swing.SwingMonoPicDrawer;
import net.richarddawkins.watchmaker.morphs.mono.swing.MonochromeMenuBuilder;
import net.richarddawkins.watchmaker.morphs.mono.swing.MonochromeSwingAppData;
import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

public class DawkinsMorphSwingAppDataFactory implements SwingAppDataFactory {


	@Override
	public Vector<String> getMorphTypes() {
		Vector<String> vec = new Vector<String>();
		for(MorphType morphType: MorphType.values())
			vec.add(morphType.getName());
		return vec;
	}
	
	protected MorphType morphType;

	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.morphs.swing.SwingAppDataFactory#getMorphType()
	 */
	@Override
	public String getMorphType() {
		return morphType.getName();
	}

	/* (non-Javadoc)
	 * @see net.richarddawkins.watchmaker.morphs.swing.SwingAppDataFactory#setMorphType(net.richarddawkins.watchmaker.morph.MorphType)
	 */
	@Override
	public void setMorphType(String name) {
		
		for(MorphType morphType: MorphType.values())
			if(name.equals(morphType.getName())) {
				this.morphType = morphType;
				this.icon = new ImageIcon(ClassicImageLoader.getPicture(name).getImage());
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
		case ARTHROMORPH:
			swingAppData = new ArthromorphSwingAppData();
			break;
		case COLOUR_BIOMORPH:
			swingAppData = new ColourSwingAppData();
			break;
		case SNAIL:
			swingAppData = new SnailSwingAppData();
			break;
		case MONOCHROME_BIOMORPH:
			swingAppData = new MonochromeSwingAppData();
			break;
		default:
			swingAppData = null;

		}
		MorphConfigFactory factory = MorphConfigFactory.getInstance(morphType);
		if (factory != null) {
			try {
				MorphConfig config = factory.createConfig();
				config.setAppData(this);
				swingAppData.setMorphConfig(config);
				performTypeSpecificSetup(swingAppData);

			} catch (MorphTypeNotSupportedException e) {
				// Just go on to the next MorphType
			}
		}
		swingAppData.setMorphViewsTabbedPane(new MorphViewsTabbedPane(swingAppData));

		return swingAppData;
	}

	protected void performTypeSpecificSetup(SwingAppData swingAppData) {

		switch (morphType) {
		case ARTHROMORPH:
			swingAppData.setMenuBuilder(new ArthromorphMenuBuilder(swingAppData));
			swingAppData.setSwingPicDrawer((SwingPicDrawer)new SwingArthroPicDrawer());
			break;
		case COLOUR_BIOMORPH:
			swingAppData.setMenuBuilder(new ColourMenuBuilder(swingAppData));
			swingAppData.setSwingPicDrawer(new SwingColourPicDrawer());
			break;
		case SNAIL:
			swingAppData.setMenuBuilder(new SnailMenuBuilder(swingAppData));
			swingAppData.setSwingPicDrawer(new SwingSnailPicDrawer());
			break;
		case MONOCHROME_BIOMORPH:
			swingAppData.setMenuBuilder(new MonochromeMenuBuilder(swingAppData));
			swingAppData.setSwingPicDrawer(new SwingMonoPicDrawer());
			break;
		default:
		}

	}



}
