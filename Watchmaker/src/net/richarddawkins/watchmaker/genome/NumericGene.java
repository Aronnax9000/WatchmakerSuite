package net.richarddawkins.watchmaker.genome;

abstract public class NumericGene extends SimpleGene {

	public NumericGene(Genome genome, String name) {
		super(genome, name);
	}
	

	protected boolean showPositiveSign = false;
	public boolean isShowPositiveSign() {
		return showPositiveSign;
	}

	public void setShowPositiveSign(boolean showPositiveSign) {
		this.showPositiveSign = showPositiveSign;
	}

	public void copy(Gene destinationGene) {
		super.copy(destinationGene);
		((NumericGene)destinationGene).setShowPositiveSign(showPositiveSign);
		
	}


}
