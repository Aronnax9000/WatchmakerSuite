package net.richarddawkins.watchmaker.morph.common;

import net.richarddawkins.watchmaker.morph.mono.MonochromeMorphConfig;

public class MorphConfigFactory {
	private MorphType morphType;
	
	protected MorphConfigFactory(MorphType morphType) {
		this.morphType = morphType;
	}
	
	public static MorphConfigFactory getInstance(MorphType morphType) {
		return new MorphConfigFactory(morphType);
	}
	public MorphConfig createConfig() throws MorphTypeNotSupportedException {
		switch(morphType) {
		case MONOCHROME_BIOMORPH:
			return new MonochromeMorphConfig();
		case COLOUR_BIOMORPH:
			throw new MorphTypeNotSupportedException();
		case SNAIL:
			throw new MorphTypeNotSupportedException();
		case ARTHROMORPH: 
			throw new MorphTypeNotSupportedException();
		default:
			return null;
		}
	}
}
	
