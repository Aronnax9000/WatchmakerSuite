package net.richarddawkins.watchmaker.morph.mono.genome;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.genome.TriangleAble;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.biomorph.Biomorph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morph.biomorph.genome.GradientGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.type.SwellType;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Pic;
import net.richarddawkins.watchmaker.morph.biomorph.geom.Point;
import net.richarddawkins.watchmaker.morph.biomorph.geom.gui.SimpleSwingPic;
import net.richarddawkins.watchmaker.morph.util.Globals;

public class MonochromeGenome extends BiomorphGenome implements TriangleAble {

    public MonochromeGenome(Morph morph) {
        super(morph);
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

    /**
     * <h2>Margins</h2>
     * <p>
     * The original Pascal Develop procedure adjusts the margin in this order.
     * </p>
     * <ul>
     * <li>At the top of the Develop routine, where, if ZeroMargin is specified,
     * the margin is initialized to an infinitesimal rectangle centered on the
     * point where drawing is to take place;</li>
     * <li>In the nested procedure Tree, where it is adjusted twice: one for the
     * supplied starting point for a line segment, and once for the end point of
     * the line segment.</li>
     * <li>After the call to tree, the margin is checked to see if the centre
     * drawing point is left of the center of the margin, or right of it. If it
     * is to the left of centre, the right hand side of the margin is moved
     * right so that the centre drawing point is at the centre of the margin.
     * Otherwise, the left side of the margin is moved to the left so that it
     * will be at the centre (this movement may be zero if it is already
     * centered: the routine does not check to see if nothing needs to be done.)
     * 
     * </li>
     * </ul>
     * <p>
     * Instead of DelayedDrawing, just pass in null if you don't want a call to
     * Drawpic at the end.
     * </p>
     * <pre>
     * procedure Develop(var biomorph: person; Here: point; ZeroMargin: boolean);
     * var
     *   DeltaRecord: DeltaArray;
     *   DeltaRecordPtr: DeltaArrayPtr;
     *   j, seg, Upextent, Downextent, wid, ht, SizeWorry, thick: integer;
     * 
     *   Running: chromosome;
     *   OldHere, Centre: Point;
     *   ExtraDistance, IncDistance: integer;
     * 
     * begin {develop}
     *   ClipBoarding := False;
     * 
     *   if zeromargin then
     *     with margin do
     *     begin
     *       left := Here.h;
     *       right := Here.h;
     *       top := Here.v;
     *       bottom := Here.v;
     *     end;
     *   Centre := Here;
     *   DeltaRecordPtr := @DeltaRecord;
     *   PlugIn(Biomorph.gene, DeltaRecordPtr);
     *   ZeroPic(MyPic, Here);
     *   with biomorph do
     *     with DeltaRecord do
     *     begin
     *       if SegNoGene &lt; 1 then
     *         SegNoGene := 1;
     *       if dGene[10] = Swell then
     *         Extradistance := Tricklegene
     *       else if dGene[10] = Shrink then
     *         Extradistance := -Tricklegene
     *       else
     *         ExtraDistance := 0;
     *       Running := gene;
     *       IncDistance := 0;
     *       for seg := 1 to SegNoGene do
     *       begin
     *         OddOne := odd(seg);
     *         if seg &gt; 1 then
     *         begin
     *           OldHere := Here;
     *           Here.v := Here.v + (SegDistGene + IncDistance) div Tricklegene;
     *           IncDistance := IncDistance + ExtraDistance;
     *           if biomorph.dGene[9] = shrink then
     *             thick := biomorph.Gene[9]
     *           else
     *             thick := 1;
     *           PicLine(MyPic, OldHere.h, Oldhere.v, Here.h, Here.v, thick);
     *           for j := 1 to 8 do
     *           begin
     *             if dGene[j] = Swell then
     *               Running[j] := Running[j] + Tricklegene;
     *             if dGene[j] = Shrink then
     *               Running[j] := Running[j] - Tricklegene;
     *           end;
     *           if Running[9] &lt; 1 then
     *             Running[9] := 1;
     *           PlugIn(Running, DeltaRecordPtr);
     *         end;
     *         SizeWorry := biomorph.SegNoGene * TwoToThe(biomorph.gene[9]);
     *         if SizeWorry &gt; WorryMax then
     *           biomorph.Gene[9] := biomorph.Gene[9] - 1;
     *         if biomorph.gene[9] &lt; 1 then
     *           biomorph.gene[9] := 1;
     *         tree(biomorph, DeltaRecordPtr, Here.h, Here.v, order, 2);
     *       end;
     *     end;
     *   with biomorph do
     *     with margin do
     *     begin
     *       if Centre.h - left &gt; right - Centre.h then
     *         right := Centre.h + (Centre.h - left)
     *       else
     *         left := Centre.h - (right - Centre.h);
     *       Upextent := Centre.v - top; {can be zero if biomorph goes down}
     *       Downextent := bottom - Centre.v;
     *       if ((SpokesGene = NSouth) or (SpokesGene = Radial)) or (TheMode = Engineering) then
     *         {Obscurely necessary to cope with erasing last Rect in Manipulation}
     *       begin
     *         if UpExtent &gt; DownExtent then
     *           bottom := Centre.v + UpExtent
     *         else
     *           top := Centre.v - DownExtent;
     *       end;
     *       if SpokesGene = Radial then
     *       begin
     *         wid := right - left;
     *         ht := bottom - top;
     *         if wid &gt; ht then
     *         begin
     *           top := centre.v - wid div 2 - 1;
     *           bottom := centre.v + wid div 2 + 1;
     *         end
     *         else
     *         begin
     *           left := centre.h - ht div 2 - 1;
     *           right := centre.h + ht div 2 + 1;
     *         end;
     *       end;
     *     end;
     *   MyPic.PicPerson := biomorph;
     * 
     * end; {develop}   
     */
    @Override
    public void develop(Graphics2D g2, Point here, boolean zeroMargin) {
        SimpleSwingPic pic = (SimpleSwingPic) morph.getPic();
        int sizeWorry;
        int[] dx = new int[8];
        int[] dy = new int[8];
        int[] running = new int[9];
        Point oldHere;
        Point centre;
        int extraDistance;
        int incDistance;

        Globals.setClipBoarding(false);
        
        if (zeroMargin) {
            pic.margin.zeroRect();
        }
        centre = (Point) here.clone();
        plugIn(new int[] { gene1.getValue(), gene2.getValue(), gene3.getValue(), gene4.getValue(), gene5.getValue(),
                gene6.getValue(), gene7.getValue(), gene8.getValue(), gene9.getValue() }, dx, dy);

        pic.zeroPic(here);

        if (segNoGene.getValue() < 1)
            segNoGene.setValue(1);

        switch (segDistGene.getGradient()) {
        case Swell:
            extraDistance = trickleGene.getValue();
            break;
        case Shrink:
            extraDistance = -trickleGene.getValue();
            break;
        case Same:
        default:
            extraDistance = 0;
        }

        running = new int[] { gene1.getValue(), gene2.getValue(), gene3.getValue(), gene4.getValue(), gene5.getValue(),
                gene6.getValue(), gene7.getValue(), gene8.getValue(), gene9.getValue() };
        incDistance = 0;
        SwellType[] dGene = new SwellType[] { gene1.getGradient(), gene2.getGradient(), gene3.getGradient(),
                gene4.getGradient(), gene5.getGradient(), gene6.getGradient(), gene7.getGradient(), gene8.getGradient(),
                gene9.getGradient()

        };
        for (int seg = 0; seg < segNoGene.getValue(); seg++) {
            oddOne = (seg & 1) == 1;
            if (seg > 0) {
                oldHere = (Point) here.clone();
                here.v += (segDistGene.getValue() + incDistance) / trickleGene.getValue();
                incDistance += extraDistance;
                int thick;
                if (gene9.getGradient() == SwellType.Shrink)
                    thick = gene9.getValue();
                else
                    thick = 1;
                pic.picLine(oldHere.h, oldHere.v, here.h, here.v, thick, Color.BLACK);
                for (int j = 0; j < 8; j++) {
                    if (dGene[j] == SwellType.Swell)
                        running[j] += trickleGene.getValue();
                    else if (dGene[j] == SwellType.Shrink)
                        running[j] -= trickleGene.getValue();
                }
                if (running[8] < 1)
                    running[8] = 1;
                plugIn(running, dx, dy);
            }
            sizeWorry = segNoGene.getValue() * (1 << gene9.getValue());
            if (sizeWorry > Globals.worryMax)
                gene9.setValue(gene9.getValue() - 1);
            if (gene9.getValue() < 1)
                gene9.setValue(1);
            tree(here.h, here.v, order, 2, dx, dy);
        }
        if (g2 != null) {
            pic.drawPic(g2, here, centre, morph);
            g2.setColor(Color.RED);
            Rectangle rectangle = pic.margin.toRectangle();
            g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }

    }

    public void insect() {
        makeGenes(Biomorph.TRICKLE, Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, Biomorph.TRICKLE, -Biomorph.TRICKLE,
                -2 * Biomorph.TRICKLE, 8 * Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, 6);
    }

    public void makeGenes(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
        super.makeGenes(a, b, c, d, e, f, g, h, i);
        segDistGene.setValue(150);
    }

    protected void plugIn(int[] gene, int[] dx, int[] dy) {
        order = gene[8];
        dx[3] = gene[0];
        dx[4] = gene[1];
        dx[5] = gene[2];
        dy[2] = gene[3];
        dy[3] = gene[4];
        dy[4] = gene[5];
        dy[5] = gene[6];
        dy[6] = gene[7];
        dx[1] = -dx[3];
        dy[1] = dy[3];
        dx[0] = -dx[4];
        dy[0] = dy[4];
        dx[7] = -dx[5];
        dy[7] = dy[5];
        dx[2] = 0;
        dx[6] = 0;
    }

    public Genome reproduce(Morph newMorph) {
        Genome childGenome = new MonochromeGenome(newMorph);
        super.copy(childGenome);
        MorphConfig config = newMorph.getMorphConfig();
        Mutagen mutagen = config.getMutagen();
        mutagen.mutate(childGenome);
        return childGenome;
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

    protected void tree(int x, int y, int lgth, int dir, int[] dx, int[] dy) {
        int thick;
        Pic pic = morph.getPic();
        int xnew, ynew;
        if (dir < 0)
            dir += 8;
        if (dir >= 8)
            dir -= 8;
        if (trickleGene.getValue() < 1)
            trickleGene.setValue(1);
        xnew = x + lgth * dx[dir] / trickleGene.getValue();
        ynew = y + lgth * dy[dir] / trickleGene.getValue();

        if (gene9.getGradient() == SwellType.Shrink)
            thick = lgth;
        else if (gene9.getGradient() == SwellType.Swell)
            thick = 1 + gene9.getValue() - lgth;
        else
            thick = 1;
        pic.picLine(x, y, xnew, ynew, thick * Globals.myPenSize, Color.BLACK);
        if (lgth > 1) {
            if (oddOne) {
                tree(xnew, ynew, lgth - 1, dir + 1, dx, dy);
                if (lgth < order)
                    tree(xnew, ynew, lgth - 1, dir - 1, dx, dy);
            } else {
                tree(xnew, ynew, lgth - 1, dir - 1, dx, dy);
                if (lgth < order)
                    tree(xnew, ynew, lgth - 1, dir + 1, dx, dy);
            }
        }
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
            ((GradientGene) getGene(j)).setGradient(
                    SwellType.values()[force3(
                            r1 * ((GradientGene)a.getGene(j)).getGradient().ordinal()
                            + r2 * ((GradientGene)b.getGene(j)).getGradient().ordinal() 
                            + r3 * ((GradientGene)c.getGene(j)).getGradient().ordinal())]);
        }
        segDistGene.setGradient(SwellType.values()[force3(
                r1 * a.segDistGene.getGradient().ordinal()
                + r2 * b.segDistGene.getGradient().ordinal() 
                + r3 * c.segDistGene.getGradient().ordinal())]);

    }
}
