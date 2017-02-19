package net.richarddawkins.watchmaker.morphs.mono.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.Triangler;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.mono.genome.type.SwellType;
import net.richarddawkins.watchmaker.util.Globals;

public class MonochromeTriangler implements Triangler {

    private static MonochromeTriangler instance;
    
    protected MonochromeTriangler() {}
    public synchronized static MonochromeTriangler getInstance() {
        if(instance == null) {
            instance = new MonochromeTriangler();
        }
        return instance;
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
	protected int force3(double r) {
		int i = (int) Math.round(r);
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
	protected int force2(double r) {
		int i = (int) Math.round(r);
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
	 * @param genericA
	 *            the first supplied genome.  An unchecked ClassCastException will occur if the genome is not of the correct type.
	 * @param genericB
	 *            the second supplied genome.  An unchecked ClassCastException will occur if the genome is not of the correct type.
	 * @param genericC
	 *            the third supplied genome. An unchecked ClassCastException will occur if the genome is not of the correct type.
	 * 
	 */
	@Override
	public void concoct(Genome genericTarget, double r1, double r2, double r3, Genome genericA, Genome genericB, Genome genericC) {
		MonochromeGenome target = (MonochromeGenome) genericTarget;
		MonochromeGenome a = (MonochromeGenome) genericA;
		MonochromeGenome b = (MonochromeGenome) genericB;
		MonochromeGenome c = (MonochromeGenome) genericC;
		int segNoGeneValue = (int) Math
				.round(r1 * a.getSegNoGene().getValue() + r2 * b.getSegNoGene().getValue() + r3 * c.getSegNoGene().getValue());
		if (segNoGeneValue < 1)
			segNoGeneValue = 1;

		target.getSegNoGene().setValue(segNoGeneValue);

		int segDistGeneValue = (int) Math
				.round(r1 * a.getSegDistGene().getValue() + r2 * b.getSegDistGene().getValue() + r3 * c.getSegDistGene().getValue());

		target.getSegDistGene().setValue(segDistGeneValue);

		target.getCompletenessGene().setValue(CompletenessType.values()[force2(
				r1 * a.getCompletenessGene().getValue().ordinal() + r2 * b.getCompletenessGene().getValue().ordinal()
						+ r3 * c.getCompletenessGene().getValue().ordinal())]);

		target.getSpokesGene().setValue(SpokesType.values()[force3(r1 * a.getSpokesGene().getValue().ordinal()
				+ r2 * b.getSpokesGene().getValue().ordinal() + r3 * c.getSpokesGene().getValue().ordinal())]);

		for (int j = 0; j < 10; j++) {
			((IntegerGene) target.getGene(j)).setValue((int)Math.round(r1 * ((IntegerGene) a.getGene(j)).getValue()
					+ r2 * ((IntegerGene) b.getGene(j)).getValue() + r3 * ((IntegerGene) c.getGene(j)).getValue()));
			long sizeWorry = (long) (target.getSegNoGene().getValue() * Math.pow(2, target.getGene9().getValue()));
			if (sizeWorry > Globals.worryMax)
				target.getGene9().decrementGene();
			if (target.getGene9().getValue() < 1)
				target.getGene9().setValue(1);
		}

		target.getTrickleGene().setValue((int)Math
				.round(r1 * a.getTrickleGene().getValue() + r2 * b.getTrickleGene().getValue() + r3 * c.getTrickleGene().getValue()));

		target.getMutSizeGene().setValue((int)Math
				.round(r1 * a.getMutSizeGene().getValue() + r2 * b.getMutSizeGene().getValue() + r3 * c.getMutSizeGene().getValue()));
		
		target.getMutProbGene().setValue((int)Math
				.round(r1 * a.getMutProbGene().getValue() + r2 * b.getMutProbGene().getValue() + r3 * c.getMutProbGene().getValue()));

		if (target.getMutProbGene().getValue() < 1)
			target.getMutProbGene().setValue(1);
		else if (target.getMutProbGene().getValue() > 100)
			target.getMutProbGene().setValue(100);
		for (int j = 0; j < 9; j++) {
			((IntegerGradientGene) target.getGene(j)).setGradient(
					SwellType.values()[force3(r1 * ((IntegerGradientGene) a.getGene(j)).getGradient().ordinal()
							+ r2 * ((IntegerGradientGene) b.getGene(j)).getGradient().ordinal()
							+ r3 * ((IntegerGradientGene) c.getGene(j)).getGradient().ordinal())]);
		}
		target.getSegDistGene().setGradient(SwellType.values()[force3(r1 * a.getSegDistGene().getGradient().ordinal()
				+ r2 * b.getSegDistGene().getGradient().ordinal() + r3 * c.getSegDistGene().getGradient().ordinal())]);

	}
}
