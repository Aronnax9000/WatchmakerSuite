package net.richarddawkins.watchmaker.morph.colour;

import net.richarddawkins.watchmaker.morph.common.BiomorphGenome;

public interface ColourPerson extends BiomorphGenome {
	public static final int RAINBOW = 256;
	public void addToBackColorGene(int summand);
	public int[] getColorGene();
	public void setColorGene(int[] colorGene);
	public long getBackColorGene();
	public void setBackColorGene(long backColorGene);
	public LimbType getLimbShapeGene();
	public void setLimbShapeGene(LimbType limbShapeGene);
	public LimbFillType getLimbFillGene();
	public void setLimbFillGene(LimbFillType limbFillGene);
	public int getThicknessGene();
	public void setThicknessGene(int thicknessGene);
	public void addToSegDistGene(int summand);
	public void addToThicknessGene(int direction9);
	public void addToColorGene(int j, int direction9);

}
