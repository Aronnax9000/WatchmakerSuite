package net.richarddawkins.watchmaker.morphs.concho;


import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.SimpleMorphConfig;

public class SnailConfig extends SimpleMorphConfig {

	protected boolean sideView = false;
	
	protected SnailMutagen mutagen;
	@Override
	public Mutagen getMutagen() {
		return mutagen;
	}
	@Override
	public void setMutagen(Mutagen mutagen) {
		this.mutagen = (SnailMutagen) mutagen;
	}
	
	public boolean isSideView() {
		return sideView;
	}

	public void setSideView(boolean sideView) {
		this.sideView = sideView;
	}

	@Override
	public Morph createMorph(int type) {
		return new Snail(this, type);
	}
//	private SnailMenuBuilder menuBuilder = new SnailMenuBuilder(this);


	public SnailConfig() {
		super(MorphType.SNAIL);

    setDefaultBreedingRows(3);
    setDefaultBreedingCols(5);
	}

	@Override
	public boolean[] getMut() {
		// TODO Auto-generated method stub
		return null;
	}






}
