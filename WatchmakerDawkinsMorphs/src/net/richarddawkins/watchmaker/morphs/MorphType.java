package net.richarddawkins.watchmaker.morphs;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import net.richarddawkins.watchmaker.resourceloader.ClassicImageLoader;

public enum MorphType {
    MONOCHROME_BIOMORPH ("Monochrome", "BWSpiderLogoMono_ICNO_23096_32x32", "Blind Watchmaker (Monochrome)"),
    COLOUR_BIOMORPH ("Colour", "BWSpiderLogoPurple_icl8_23096_32x32", "Blind Watchmaker (Colour)"),
    SNAIL ("Snails", "SnailLogoBlackBackground_icl4_17669_32x32", "Blind Snailmaker"),
    ARTHROMORPH ("Arthromorphs", "Arthromorph_ALAN_00010_32x32", "Arthromorphs");
	private final String name;
	private final Icon icon;
	private final String toolTip;
	
	MorphType(String name, String icon, String toolTip) {
		this.name = name;
		this.icon = new ImageIcon(
				ClassicImageLoader.getPicture(icon).getImage());
		this.toolTip = toolTip;
	}
	
	public String getName() {
		return name;
	}
	public Icon getIcon() {
		return icon;
	}
	public String getToolTip() {
		return toolTip;
	}
    
}