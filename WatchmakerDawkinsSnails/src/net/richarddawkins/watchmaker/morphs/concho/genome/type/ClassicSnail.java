package net.richarddawkins.watchmaker.morphs.concho.genome.type;
/**
 * Dawkins did not provide explicit mutation probabilities in all cases.
 * I have used the value "100" where he did not provide a value. This should
 * probably be changed to whatever the initialization value is for mutProb in
 * the Pascal sources.
 * @author Alan Canon
 *
 */
public enum ClassicSnail {

	
	Snail(1.66, 0, 1.2, 2, 100, 4, 5, 0, HandednessType.Right, 1, null), 
	Turritella(1.30, 0, 1, 8.2, 100, 8, 10, 0, HandednessType.Right, 1, null),
	Bivalve(1000, 0, 1.2, 0.5, 50, 2, 1, 0, HandednessType.Right, 1, null),
	Ammonite(2, 0, 1, 0, 100, 8, 3, 0, HandednessType.Right, 1, null),
	Nautilus(3.4, 0, 1.2, 0, 100, 8, 3, 0, HandednessType.Right, 1, null),
	Brachiopod(10000, 0, 1, 0, 100, 2, 3, 0, HandednessType.Right, 1, null),
	Cone(1.66, 0, 3, 3.5, 100, 2, 3, 128, HandednessType.Right, 1, "SnailOutline128ConeWhelkARGB_PICT_00128_113x171"),
	Whelk(1.7, 0, 2, 4, 100, 2, 6, 128, HandednessType.Right, 1, "SnailOutline128ConeWhelkARGB_PICT_00128_113x171"),
	Scallop(10000, 0, 1, 0, 2, 100, 3, 148, HandednessType.Right, 1, "SnailOutline148ScallopARGB_PICT_00148_232x231"),
	Eloise(1.4, 0, 1.7, 3.5, 100, 4, 6, 146, HandednessType.Right, 1, "SnailOutline146EloiseARGB_PICT_00146_91x172"),
	Gallaghers(1.66, 0, 1.8, 5, 100, 4, 6, 136, HandednessType.Left, 1, "SnailOutline136GallaghersARGB_PICT_00136_78x141"),
	Rapa(1.66, 0, 2, 2.2, 100, 4, 9, 132, HandednessType.Right, 1, "SnailOutline132RapaARGB_PICT_00132_104x181"),
	Lightning(1.66, 0, 3.5, 4, 100, 4, 6, 150, HandednessType.Left, 0.9, "SnailOutline150LightningARGB_PICT_00150_57x173"),
	Sundial(1.384, 0.261, 0.618, 1.055, 100, 2, 10, 152, HandednessType.Right, 1, "SnailOutline152SundialARGB_PICT_00152_259x171"),
	Fig(2, 0, 3, 3.5, 100, 2, 8, 134, HandednessType.Right, 0.95, "SnailOutline134FigTunARGB_PICT_00134_77x212"),
	Tun(2, 0, 2, 2.8, 100, 2, 8, 134, HandednessType.Right, 1, "SnailOutline134FigTunARGB_PICT_00134_77x212"),
	RazorShell(1000, 0, 8, 6, 50, 2, 1, 138, HandednessType.Right, 1, "SnailOutline138RazorShellARGB_PICT_00138_46x214"),
	JapaneseWonder(1.7, 0, 1.3, 4.2, 50, 2, 8, 130, HandednessType.Right, 1, "SnailOutline130JapaneseWonderARGB_PICT_00130_105x176");

	public double getOpening() {
		return opening;
	}

	public double getDisplacement() {
		return displacement;
	}

	public double getShape() {
		return shape;
	}

	public double getTranslation() {
		return translation;
	}

	public int getCoarsegraininess() {
		return coarsegraininess;
	}

	public int getReach() {
		return reach;
	}

	public int getGeneratingCurve() {
		return generatingCurve;
	}

	public HandednessType getHandedness() {
		return handedness;
	}

	public double getTranslationGradient() {
		return translationGradient;
	}

	private final double opening;
	private final double displacement;
	private final double shape;
	private final double translation;
	private final int mutProb;
	public int getMutProb() {
		return mutProb;
	}

	private final int coarsegraininess;
	private final int reach;
	private final int generatingCurve;
	private final HandednessType handedness;
	private final double translationGradient;
	private final String genCurveImageName;

	ClassicSnail(double opening, double displacement, double shape, double translation, int mutProb,
			int coarsegraininess, int reach, int generatingCurve, HandednessType handedness,
			double translationGradient, String genCurveImageName) {
		this.opening = opening;
		this.displacement = displacement;
		this.shape = shape;
		this.translation = translation;
		this.mutProb = mutProb;
		this.coarsegraininess = coarsegraininess;
		this.reach = reach;
		this.generatingCurve = generatingCurve;
		this.handedness = handedness;
		this.translationGradient = translationGradient;
		this.genCurveImageName = genCurveImageName;
	}
}
