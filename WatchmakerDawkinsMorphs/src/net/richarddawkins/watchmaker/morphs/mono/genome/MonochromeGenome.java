package net.richarddawkins.watchmaker.morphs.mono.genome;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.SimpleGenome;
import net.richarddawkins.watchmaker.genome.TriangleAble;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SwellType;
import net.richarddawkins.watchmaker.util.Globals;

public class MonochromeGenome extends SimpleGenome implements TriangleAble {
	private static Logger logger = Logger
			.getLogger("net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome");

	public MonochromeGenome() {
		gene9.setGene9Max(11);
		segNoGene.setShowPositiveSign(true);
		segDistGene.setShowPositiveSign(true);
		trickleGene.setShowPositiveSign(true);
		mutSizeGene.setShowPositiveSign(true);
		mutProbGene.setShowPositiveSign(true);
	}
	protected final Gene12345678 gene1 = new Gene12345678(this, "Gene 1");
	protected final Gene12345678 gene2 = new Gene12345678(this, "Gene 2");
	protected final Gene12345678 gene3 = new Gene12345678(this, "Gene 3");
	protected final Gene12345678 gene4 = new Gene12345678(this, "Gene 4");
	protected final Gene12345678 gene5 = new Gene12345678(this, "Gene 5");
	protected final Gene12345678 gene6 = new Gene12345678(this, "Gene 6");
	protected final Gene12345678 gene7 = new Gene12345678(this, "Gene 7");
	protected final Gene12345678 gene8 = new Gene12345678(this, "Gene 8");
	protected final Gene9 gene9 = new Gene9(this, "Gene 9");
	protected final SegNoGene segNoGene = new SegNoGene(this, "Segment Number");
	protected final IntegerGradientGene segDistGene = new IntegerGradientGene(this, "Segment Distance");
	protected final CompletenessGene completenessGene = new CompletenessGene(this, "Completeness");
	protected final SpokesGene spokesGene = new SpokesGene(this, "Spokes");
	protected final IntegerGeneOneOrGreater trickleGene = new IntegerGeneOneOrGreater(this, "Trickle");
	protected final IntegerGeneOneOrGreater mutSizeGene = new IntegerGeneOneOrGreater(this, "Mutation Size");
	protected final IntegerGene1To100 mutProbGene = new IntegerGene1To100(this, "Mutation Probability");



	
	public Gene12345678 getGene1() {
		return gene1;
	}

	public Gene12345678 getGene2() {
		return gene2;
	}

	public Gene12345678 getGene3() {
		return gene3;
	}

	public Gene12345678 getGene4() {
		return gene4;
	}

	public Gene12345678 getGene5() {
		return gene5;
	}

	public Gene12345678 getGene6() {
		return gene6;
	}

	public Gene12345678 getGene7() {
		return gene7;
	}

	public Gene12345678 getGene8() {
		return gene8;
	}

	public Gene9 getGene9() {
		return gene9;
	}

	public SegNoGene getSegNoGene() {
		return segNoGene;
	}

	public IntegerGradientGene getSegDistGene() {
		return segDistGene;
	}

	public CompletenessGene getCompletenessGene() {
		return completenessGene;
	}

	public SpokesGene getSpokesGene() {
		return spokesGene;
	}

	public IntegerGeneOneOrGreater getTrickleGene() {
		return trickleGene;
	}

	public IntegerGeneOneOrGreater getMutSizeGene() {
		return mutSizeGene;
	}

	public IntegerGene1To100 getMutProbGene() {
		return mutProbGene;
	}

    @Override
    public Gene[] toGeneArray() {
        Gene[] theGenes = new Gene[16];

        theGenes[0] = gene1;
        theGenes[1] = gene2;
        theGenes[2] = gene3;
        theGenes[3] = gene4;
        theGenes[4] = gene5;
        theGenes[5] = gene6;
        theGenes[6] = gene7;
        theGenes[7] = gene8;
        theGenes[8] = gene9;
        theGenes[9] = segNoGene;
        theGenes[10] = segDistGene;
        theGenes[11] = completenessGene;
        theGenes[12] = spokesGene;
        theGenes[13] = trickleGene;
        theGenes[14] = mutSizeGene;
        theGenes[15] = mutProbGene;

        return theGenes;
    }
	/**
	 * Return 0, 1, or 2 depending on the range of the rounded value of a
	 * floating point number.
	 * <h2>Original Pascal Source Code</h2>
	 * <p>
	 * This function is a nested function within procedure Concoct.
	 * 
	 * <pre>
	 *    function Force3 (r: real): Integer;
	 *        var
	 *            i: Integer;
	 *    begin
	 *        i := round(r);
	 *        if i &gt; 2 then
	 *            i := 2;
	 *        if i &lt; 0 then
	 *            i := 0;
	 *        Force3 := i
	 *    end; {Force3}
	 * </pre>
	 * 
	 * @param r
	 *            a floating point number
	 * @return 0 if the rounded number is 0 or less, 1 if the rounded number is
	 *         1 or greater, and less than 2, 2 otherwise.
	 */
	protected int force3(float r) {
		int i = Math.round(r);
		if (i > 2) {
			i = 2;
		} else if (i < 0) {
			i = 0;
		}
		return i;
	}

	/**
	 * Return a 0 or 1 depending on the rounded value of a floating point
	 * number.
	 * <h2>Original Pascal Source Code</h2>
	 * <p>
	 * This function is a nested function within procedure Concoct.
	 * 
	 * <pre>
	 *     function Force2 (r: real): Integer;
	 *        var
	 *            i: Integer;
	 *    begin
	 *        i := round(r);
	 *        if i &gt; 1 then
	 *            i := 1;
	 *        if i &lt; 0 then
	 *            i := 0;
	 *        Force2 := i
	 *    end; {Force2}
	 * </pre>
	 * 
	 * @param r
	 *            a floating point number
	 * @return 0 if the rounded number is 0 or less, 1 if the rounded number is
	 *         1 or greater
	 */
	protected int force2(float r) {
		int i = Math.round(r);
		if (i > 1) {
			i = 1;
		} else if (i < 0) {
			i = 0;
		}
		return i;
	}

	/**
	 * Sets the genes in the current genome to values that are a weighted
	 * average of three supplied genomes.
	 * <h2>Original Pascal Source Code</h2>
	 * 
	 * <pre>
	 *
	 *procedure Concoct (r1, r2, r3: real; a, b, c: person; var new: person);
	 *var
	 *    j, weight: Integer;
	 *begin
	 *    with new do
	 *        begin
	 *            SegNoGene := round(r1 * a.SegNoGene + r2 * b.SegNoGene + r3 * c.SegNoGene);
	 *            if SegNoGene &lt; 1 then
	 *                SegNoGene := 1;
	 *            SegDistGene := round(r1 * a.SegDistGene + r2 * b.SegDistGene + r3 * c.SegDistGene);
	 *            CompletenessGene := CompletenessType(Force2(r1 * Integer(a.CompletenessGene) + r2 * Integer(b.CompletenessGene) + r3 * Integer(c.CompletenessGene)));
	 *            SpokesGene := SpokesType(Force3(r1 * Integer(a.SpokesGene) + r2 * Integer(b.SpokesGene) + r3 * Integer(c.SpokesGene)));
	 *            for j := 1 to 9 do
	 *                gene[j] := round(r1 * a.gene[j] + r2 * b.gene[j] + r3 * c.gene[j]);
	 *            SizeWorry := SegNoGene * TwoToThe(gene[9]);
	 *            if SizeWorry &gt; WorryMax then
	 *                Gene[9] := Gene[9] - 1;
	 *            if gene[9] &lt; 1 then
	 *                gene[9] := 1;
	 *            tricklegene := round(r1 * a.tricklegene + r2 * b.tricklegene + r3 * c.tricklegene);
	 *            MutSizeGene := round(r1 * a.MutSizeGene + r2 * b.MutSizeGene + r3 * c.MutSizeGene);
	 *            MutProbGene := round(r1 * a.MutProbGene + r2 * b.MutProbGene + r3 * c.MutProbGene);
	 *            if mutprobgene &lt; 1 then
	 *                mutprobgene := 1;
	 *            if mutprobgene &gt; 100 then
	 *                mutprobgene := 100;
	 *            for j := 1 to 10 do
	 *                dgene[j] := swelltype(Force3(r1 * Integer(a.dgene[j]) + r2 * Integer(b.dgene[j]) + r3 * Integer(c.dgene[j])));
	 *        end
	 *end; {concoct}
	 * </pre>
	 * 
	 * @param r1
	 *            the weight to assign to the first supplied genome
	 * @param r2
	 *            the weight to assign to the second supplied genome
	 * @param r3
	 *            the weight to assign to the third supplied genome
	 * @param a
	 *            the first supplied genome
	 * @param b
	 *            the second supplied genome
	 * @param c
	 *            the third supplied genome
	 * 
	 */
	protected void concoct(float r1, float r2, float r3, MonochromeGenome a, MonochromeGenome b, MonochromeGenome c) {

		int segNoGeneValue = Math
				.round(r1 * a.segNoGene.getValue() + r2 * b.segNoGene.getValue() + r3 * c.segNoGene.getValue());
		if (segNoGeneValue < 1)
			segNoGeneValue = 1;

		this.segNoGene.setValue(segNoGeneValue);

		int segDistGeneValue = Math
				.round(r1 * a.segDistGene.getValue() + r2 * b.segDistGene.getValue() + r3 * c.segDistGene.getValue());

		segDistGene.setValue(segDistGeneValue);

		completenessGene.setValue(CompletenessType.values()[force2(
				r1 * a.getCompletenessGene().getValue().ordinal() + r2 * b.getCompletenessGene().getValue().ordinal()
						+ r3 * c.getCompletenessGene().getValue().ordinal())]);

		spokesGene.setValue(SpokesType.values()[force3(r1 * a.getSpokesGene().getValue().ordinal()
				+ r2 * b.getSpokesGene().getValue().ordinal() + r3 * c.getSpokesGene().getValue().ordinal())]);

		for (int j = 0; j < 10; j++) {
			((IntegerGene) getGene(j)).setValue(Math.round(r1 * ((IntegerGene) a.getGene(j)).getValue()
					+ r2 * ((IntegerGene) b.getGene(j)).getValue() + r3 * ((IntegerGene) c.getGene(j)).getValue()));
			long sizeWorry = (long) (segNoGene.getValue() * Math.pow(2, gene9.getValue()));
			if (sizeWorry > Globals.worryMax)
				gene9.decrementGene();
			if (gene9.getValue() < 1)
				gene9.setValue(1);
		}

		trickleGene.setValue(Math
				.round(r1 * a.trickleGene.getValue() + r2 * b.trickleGene.getValue() + r3 * c.trickleGene.getValue()));

		mutSizeGene.setValue(Math
				.round(r1 * a.mutSizeGene.getValue() + r2 * b.mutSizeGene.getValue() + r3 * c.mutSizeGene.getValue()));

		mutProbGene.setValue(Math
				.round(r1 * a.mutProbGene.getValue() + r2 * b.mutProbGene.getValue() + r3 * c.mutProbGene.getValue()));

		if (mutProbGene.getValue() < 1)
			mutProbGene.setValue(1);
		else if (mutProbGene.getValue() > 100)
			mutProbGene.setValue(100);
		for (int j = 0; j < 9; j++) {
			((IntegerGradientGene) getGene(j)).setGradient(
					SwellType.values()[force3(r1 * ((IntegerGradientGene) a.getGene(j)).getGradient().ordinal()
							+ r2 * ((IntegerGradientGene) b.getGene(j)).getGradient().ordinal()
							+ r3 * ((IntegerGradientGene) c.getGene(j)).getGradient().ordinal())]);
		}
		segDistGene.setGradient(SwellType.values()[force3(r1 * a.segDistGene.getGradient().ordinal()
				+ r2 * b.segDistGene.getGradient().ordinal() + r3 * c.segDistGene.getGradient().ordinal())]);

	}
	@Override
    public void readFromByteBuffer(ByteBuffer byteBuffer) {
		Gene[] genes = toGeneArray();
    	for(int index = 0; index < 9; index++) {
    		genes[index].readIndexedValueFromByteBuffer(byteBuffer, 0);
    		logger.fine(this.toString());
    	}
		// Grabs the ten gradient genes, for Gene1-Gene9 and SegDistGene
		for(int index: new int[] {0,1,2,3,4,5,6,7,8,10}) {
			genes[index].readIndexedValueFromByteBuffer(byteBuffer, 1);
    		logger.fine(this.toString());
		}
		segNoGene.readValueFromByteBuffer(byteBuffer);
		// Note gradient already read in: this just grabs numeric value.
		logger.fine(this.toString());
		segDistGene.readValueFromByteBuffer(byteBuffer);
		logger.fine(this.toString());
		completenessGene.readValueFromByteBuffer(byteBuffer);
		logger.fine(this.toString());
		spokesGene.readValueFromByteBuffer(byteBuffer);
		logger.fine(this.toString());
		trickleGene.readValueFromByteBuffer(byteBuffer);
		logger.fine(this.toString());
		mutSizeGene.readValueFromByteBuffer(byteBuffer);
		logger.fine(this.toString());
		mutProbGene.readValueFromByteBuffer(byteBuffer);
		logger.fine(this.toString());
    }
    @Override
    public void writeToByteBuffer(ByteBuffer byteBuffer) {
    	Gene[] genes = toGeneArray();
    	for(int index = 0; index < 8; index++) {
    		genes[index].writeIndexedValueToByteBuffer(byteBuffer, 0);
    	}
		// Remember to add SegDistGene code after this for Gene 
		for(int index: new int[] {0,1,2,3,4,5,6,7,8,10}) {
			genes[index].writeIndexedValueToByteBuffer(byteBuffer, 1);
		}
		segNoGene.writeValueToByteBuffer(byteBuffer);
		// Note gradient already read in: this just grabs numeric value.
		segDistGene.writeValueToByteBuffer(byteBuffer);
		completenessGene.writeValueToByteBuffer(byteBuffer);
		spokesGene.writeValueToByteBuffer(byteBuffer);
		trickleGene.writeValueToByteBuffer(byteBuffer);
		mutSizeGene.writeValueToByteBuffer(byteBuffer);
		mutProbGene.writeValueToByteBuffer(byteBuffer);
    }

	
}
