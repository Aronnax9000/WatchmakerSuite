package net.richarddawkins.watchmaker.morph.mono;

import net.richarddawkins.watchmaker.genome.gui.GeneBoxStrip;
import net.richarddawkins.watchmaker.gui.menu.MenuBuilder;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.biomorph.BiomorphConfig;
import net.richarddawkins.watchmaker.morph.mono.genome.MonochromeMutagen;
import net.richarddawkins.watchmaker.morph.mono.geom.gui.SwingMonoPicDrawer;
import net.richarddawkins.watchmaker.morph.mono.gui.MonochromeGeneBoxStrip;
import net.richarddawkins.watchmaker.morph.mono.gui.MonochromeMenuBuilder;

public class MonochromeMorphConfig extends BiomorphConfig {

	public static final int MutTypeNo = 9;


	protected MonochromeMutagen mutagen;

	

	public Mutagen getMutagen() {
		return mutagen;
	}

	public MonochromeMorphConfig() {
		super(MorphType.MONOCHROME_BIOMORPH);
		geneBoxCount = 16;
		
		mut = new boolean[MutTypeNo];
		for (int i = 0; i < MutTypeNo; i++)
			mut[i] = true;

		setDefaultBreedingRows(3);
		setDefaultBreedingCols(5);
		menuBuilder = new MonochromeMenuBuilder(this);
		mutagen = new MonochromeMutagen(this);
		swingPicDrawer = new SwingMonoPicDrawer();
		
	}

	@Override
	public Morph createMorph(int type) {
		return (Morph) new MonochromeMorph(this, type);
	}


	@Override
	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (MonochromeMutagen) mutagen;

	}
	
	@Override
	public GeneBoxStrip newGeneBoxStrip(boolean engineeringMode) {

		return new MonochromeGeneBoxStrip(this, engineeringMode);
	}

	
	



}
