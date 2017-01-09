package net.richarddawkins.watchmaker.genome;

public class NumericGene extends SimpleGene {
	

	protected boolean showPositiveSign = false;
	public boolean isShowPositiveSign() {
		return showPositiveSign;
	}

	public void setShowPositiveSign(boolean showPositiveSign) {
		this.showPositiveSign = showPositiveSign;
	}
}
