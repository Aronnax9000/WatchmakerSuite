package net.richarddawkins.watchmaker.morphs.mono.genome;

import java.util.logging.Logger;

import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.TriangleAble;
import net.richarddawkins.watchmaker.morphs.bio.Biomorph;
import net.richarddawkins.watchmaker.morphs.bio.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;
import net.richarddawkins.watchmaker.util.Globals;

public class MonochromeGenome extends BiomorphGenome implements TriangleAble {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger("net.richarddawkins.watchmaker.morphs.mono.genome.MonochromeGenome");

    public MonochromeGenome() {
        
        setGene9Max(11);
        segNoGene.setShowPositiveSign(true);
        segDistGene.setShowPositiveSign(true);
        trickleGene.setShowPositiveSign(true);
        mutSizeGene.setShowPositiveSign(true);
        mutProbGene.setShowPositiveSign(true);
        

    }

    public void basicTree() {
        makeGenes(-10, -20, -20, -15, -15, 0, 15, 15, 7);
        segNoGene.setValue(2);
        segDistGene.setValue(150);
        completenessGene.setValue(CompletenessType.Single);
        gene4.setGradient(SwellType.Shrink);
        gene5.setGradient(SwellType.Shrink);
        gene6.setGradient(SwellType.Shrink);
        gene9.setGradient(SwellType.Shrink);
        trickleGene.setValue(9);
        
    }

    /**
     * 
     */
    public void chess() {
        makeGenes(-Biomorph.TRICKLE, 3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE,
                Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 6 * Biomorph.TRICKLE, -5 * Biomorph.TRICKLE, 7);
    }



    public void insect() {
        makeGenes(Biomorph.TRICKLE, Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, Biomorph.TRICKLE, -Biomorph.TRICKLE,
                -2 * Biomorph.TRICKLE, 8 * Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, 6);
    }

    public void makeGenes(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
        super.makeGenes(a, b, c, d, e, f, g, h, i);
        segDistGene.setValue(150);
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

        for(int j = 0; j < 10; j++) {
            ((IntegerGene) getGene(j)).setValue(
                    Math.round(r1 * ((IntegerGene) a.getGene(j)).getValue()
                            + r2 * ((IntegerGene) b.getGene(j)).getValue()
                            + r3 * ((IntegerGene) c.getGene(j)).getValue()
                    ));
            long sizeWorry = (long) (segNoGene.getValue() * Math.pow(2,  gene9.getValue()));
            if(sizeWorry > Globals.worryMax)
                gene9.decrementGene();
            if(gene9.getValue() < 1) 
                gene9.setValue(1);
        }
        
        trickleGene.setValue(
                Math.round(r1 * a.trickleGene.getValue()
                        + r2 * b.trickleGene.getValue()
                        + r3 * c.trickleGene.getValue()
                ));

        mutSizeGene.setValue(
                Math.round(r1 * a.mutSizeGene.getValue()
                        + r2 * b.mutSizeGene.getValue()
                        + r3 * c.mutSizeGene.getValue()
                ));

        mutProbGene.setValue(
                Math.round(r1 * a.mutProbGene.getValue()
                        + r2 * b.mutProbGene.getValue()
                        + r3 * c.mutProbGene.getValue()
                ));
        
        if(mutProbGene.getValue() < 1) mutProbGene.setValue(1);
        else if(mutProbGene.getValue() > 100) mutProbGene.setValue(100);
        for(int j = 0; j < 9; j++) {
            ((IntegerGradientGene) getGene(j)).setGradient(
                    SwellType.values()[force3(
                            r1 * ((IntegerGradientGene)a.getGene(j)).getGradient().ordinal()
                            + r2 * ((IntegerGradientGene)b.getGene(j)).getGradient().ordinal() 
                            + r3 * ((IntegerGradientGene)c.getGene(j)).getGradient().ordinal())]);
        }
        segDistGene.setGradient(SwellType.values()[force3(
                r1 * a.segDistGene.getGradient().ordinal()
                + r2 * b.segDistGene.getGradient().ordinal() 
                + r3 * c.segDistGene.getGradient().ordinal())]);

    }
}
