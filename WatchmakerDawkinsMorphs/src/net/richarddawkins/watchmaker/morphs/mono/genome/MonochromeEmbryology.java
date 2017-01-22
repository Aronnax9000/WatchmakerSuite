package net.richarddawkins.watchmaker.morphs.mono.genome;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.geom.Phenotype;
import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.morphs.bio.embryo.BiomorphEmbryology;
import net.richarddawkins.watchmaker.morphs.bio.genome.Gene9;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGeneZeroOrGreater;
import net.richarddawkins.watchmaker.morphs.bio.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morphs.bio.genome.type.SwellType;
import net.richarddawkins.watchmaker.morphs.mono.geom.MonoPic;
import net.richarddawkins.watchmaker.util.Globals;

public class MonochromeEmbryology extends BiomorphEmbryology {
	
    protected void tree(MonochromeGenome genome, MonoPic pic, int x, int y, int lgth, int dir, int[] dx, int[] dy, int order, boolean oddOne) {
    	Gene9 gene9 = genome.getGene9();
    	IntegerGeneZeroOrGreater trickleGene = genome.getTrickleGene();

    	int thick;
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
        pic.picLine(genome, x, y, xnew, ynew, thick * Globals.myPenSize);
        if (lgth > 1) {
            if (oddOne) {
                tree(genome, pic, xnew, ynew, lgth - 1, dir + 1, dx, dy, order, oddOne);
                if (lgth < order)
                    tree(genome, pic, xnew, ynew, lgth - 1, dir - 1, dx, dy, order, oddOne);
            } else {
                tree(genome, pic, xnew, ynew, lgth - 1, dir - 1, dx, dy, order, oddOne);
                if (lgth < order)
                    tree(genome, pic, xnew, ynew, lgth - 1, dir + 1, dx, dy, order, oddOne);
            }
        }
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
     * </pre>
     */
	
	@Override
	public void develop(Genome monochromeGenome, Phenotype monoPic) {
		super.develop(monochromeGenome, monoPic);
		
		MonochromeGenome genome = (MonochromeGenome) monochromeGenome;
		MonoPic pic = (MonoPic) monoPic;
        int sizeWorry;
        int[] dx = new int[8];
        int[] dy = new int[8];
        int[] running = new int[9];
        Point here = new Point(0,0);
        Point oldHere;
        int extraDistance;
        int incDistance;

    	IntegerGradientGene gene1 = genome.getGene1();
    	IntegerGradientGene gene2 = genome.getGene2();
    	IntegerGradientGene gene3 = genome.getGene3();
    	IntegerGradientGene gene4 = genome.getGene4();
    	IntegerGradientGene gene5 = genome.getGene5();
    	IntegerGradientGene gene6 = genome.getGene6();
    	IntegerGradientGene gene7 = genome.getGene7();
    	IntegerGradientGene gene8 = genome.getGene8();
    	IntegerGradientGene gene9 = genome.getGene9();
    	IntegerGene segNoGene = genome.getSegNoGene();
    	IntegerGradientGene segDistGene = genome.getSegDistGene();
    	IntegerGene trickleGene = genome.getTrickleGene();

        pic.zero();
        pic.margin.zeroRect();
        int order = plugIn(new int[] { gene1.getValue(), gene2.getValue(), gene3.getValue(), gene4.getValue(), gene5.getValue(),
                gene6.getValue(), gene7.getValue(), gene8.getValue(), gene9.getValue() }, dx, dy);


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
            boolean oddOne = (seg & 1) == 1;
            if (seg > 0) {
                oldHere = (Point) here.clone();
                here.v += (segDistGene.getValue() + incDistance) / trickleGene.getValue();
                incDistance += extraDistance;
                int thick;
                if (gene9.getGradient() == SwellType.Shrink)
                    thick = gene9.getValue();
                else
                    thick = 1;
                pic.picLine(genome, oldHere.h, oldHere.v, here.h, here.v, thick);
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
            tree(genome, pic, here.h, here.v, order, 2, dx, dy, order, oddOne);
        }
	}
}
