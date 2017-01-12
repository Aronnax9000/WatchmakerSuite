package net.richarddawkins.watchmaker.genome;

abstract public class NumericGene extends SimpleGene {

	public NumericGene(String name) {
		super(name);
	}
	

	protected boolean showPositiveSign = false;
	public boolean isShowPositiveSign() {
		return showPositiveSign;
	}

	public void setShowPositiveSign(boolean showPositiveSign) {
		this.showPositiveSign = showPositiveSign;
	}

	public void copy(NumericGene destinationGene) {
		destinationGene.setShowPositiveSign(showPositiveSign);
		
	}
}
