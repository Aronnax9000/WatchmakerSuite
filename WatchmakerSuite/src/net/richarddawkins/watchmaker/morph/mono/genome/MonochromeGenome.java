package net.richarddawkins.watchmaker.morph.mono.genome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.Mutagen;
import net.richarddawkins.watchmaker.morph.biomorph.Biomorph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.IntegerGradientGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SpokesGene;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SwellType;
import net.richarddawkins.watchmaker.morph.common.geom.Pic;
import net.richarddawkins.watchmaker.morph.common.geom.Point;
import net.richarddawkins.watchmaker.morph.util.Globals;
import net.richarddawkins.watchmaker.morph.util.ModeType;

public class MonochromeGenome extends BiomorphGenome {
	
  protected Gene[] theGenes = new Gene[16];
  
  protected Gene gene1 = new IntegerGradientGene();
  protected Gene gene2 = new IntegerGradientGene();
  protected Gene gene3 = new IntegerGradientGene();
  protected Gene gene4 = new IntegerGradientGene();
  protected Gene gene5 = new IntegerGradientGene();
  protected Gene gene6 = new IntegerGradientGene();
  protected Gene gene7 = new IntegerGradientGene();
  protected Gene gene8 = new IntegerGradientGene();
  protected Gene gene9 = new IntegerGradientGene();
  protected Gene newSegNoGene = new IntegerGene();
  protected Gene newSegDistGene = new IntegerGradientGene();
  protected Gene newCompletenessGene = new CompletenessGene();
  protected Gene newSpokesGene = new SpokesGene();
  protected Gene newTrickleGene = new IntegerGene();
  protected Gene newMutSizeGene = new IntegerGene();
  protected Gene newMutProbGene = new IntegerGene();

  
  
  {
	  theGenes[0] = gene1;
	  theGenes[1] = gene2;
	  theGenes[2] = gene3;
	  theGenes[3] = gene4;
	  theGenes[4] = gene5;
	  theGenes[5] = gene6;
	  theGenes[6] = gene7;
	  theGenes[7] = gene8;
	  theGenes[8] = gene9;
	  theGenes[9] = newSegNoGene;
	  theGenes[10] = newSegDistGene;
	  theGenes[11] = newCompletenessGene;
	  theGenes[12] = newSpokesGene;
	  theGenes[13] = newTrickleGene;
	  theGenes[14] = newMutSizeGene;
	  theGenes[15] = newMutProbGene;
	  
	  for(int i = 0; i < theGenes.length; i++)
		  	theGenes[i].setGenome(this);
  }
  
  public MonochromeGenome() {
	  
  }

  public MonochromeGenome(Morph morph) {
    setMorph(morph);
    setGene9Max(11);
  }

  void tree(int x, int y, int lgth, int dir, int[] dx, int[] dy) {
    int thick;
    Pic pic = morph.getPic();
    int xnew, ynew;
    if (dir < 0)
      dir += 8;
    if (dir >= 8)
      dir -= 8;
    if (trickleGene < 1)
      trickleGene = 1;
    xnew = x + lgth * dx[dir] / trickleGene;
    ynew = y + lgth * dy[dir] / trickleGene;


    if (dGene[8] == SwellType.Shrink)
      thick = lgth;
    else if (dGene[8] == SwellType.Swell)
      thick = 1 + gene[8] - lgth;
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

  public Genome reproduce(Morph newMorph) {
    MonochromeGenome childGenome = new MonochromeGenome(newMorph);
    super.copy(childGenome);
    MorphConfig config = newMorph.getMorphConfig();
    Mutagen mutagen = config.getMutagen();
    mutagen.mutate(childGenome);
    return childGenome;
  }

  void plugIn(int[] gene, int[] dx, int[] dy) {
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

  public void generatePic() {
	    Pic pic = morph.getPic();
	    int sizeWorry;
	    int[] dx = new int[8];
	    int[] dy = new int[8];
	    int[] running = new int[9];
	    Point oldHere;
	    int extraDistance;
	    int incDistance;
	    Point here = new Point(0,0);
	    plugIn(gene, dx, dy);
	    pic.zeroPic(here);
	    if (segNoGene < 1)
	      segNoGene = 1;
	    if (dGene[9] == SwellType.Swell)
	      extraDistance = trickleGene;
	    else if (dGene[9] == SwellType.Shrink)
	      extraDistance = -trickleGene;
	    else
	      extraDistance = 0;

	    running = gene.clone();
	    incDistance = 0;
	    for (int seg = 0; seg < segNoGene; seg++) {
	      oddOne = seg % 2 == 1;
	      if (seg > 0) {
	        oldHere = (Point) here.clone();
	        here.v += (segDistGene + incDistance) / trickleGene;
	        incDistance += extraDistance;
	        int thick;
	        if (dGene[8] == SwellType.Shrink)
	          thick = gene[8];
	        else
	          thick = 1;
	        pic.picLine(oldHere.h, oldHere.v, here.h, here.v, thick, Color.BLACK);
	        for (int j = 0; j < 8; j++) {
	          if (dGene[j] == SwellType.Swell)
	            running[j] += trickleGene;
	          else if (dGene[j] == SwellType.Shrink)
	            running[j] -= trickleGene;
	        }
	        if (running[8] < 1)
	          running[8] = 1;
	        plugIn(running, dx, dy);
	      }
	      sizeWorry = segNoGene * (1 << gene[8]);
	      if (sizeWorry > Globals.worryMax)
	        gene[8]--;
	      if (gene[8] < 1)
	        gene[8] = 1;
	      tree(here.h, here.v, order, 2, dx, dy);
	    }

	  }

  
  /**
   * <h2>Margins</h2>
   * <p>The original Pascal Develop procedure adjusts the margin in this order.</p>
   * <ul>
   * <li>At the top of the Develop routine, where, if ZeroMargin is specified, the margin is
   * initialized to an infinitesimal rectangle centered on the point where drawing is to take place;
   * </li>
   * <li>In the nested procedure Tree, where it is adjusted twice: one for the supplied starting
   * point for a line segment, and once for the end point of the line segment.</li>
   * <li>After the call to tree, the margin is checked to see if the centre drawing point is left of
   * the center of the margin, or right of it. If it is to the left of centre, the right hand side
   * of the margin is moved right so that the centre drawing point is at the centre of the margin.
   * Otherwise, the left side of the margin is moved to the left so that it will be at the centre
   * (this movement may be zero if it is already centered: the routine does not check to see if
   * nothing needs to be done.)
   * 
   * </li>
   * </ul>
   * <p>
   * Instead of DelayedDrawing, just pass in null if you don't want a call to Drawpic at the end.
   * </p>
   */
  public void develop(Graphics2D g2, Dimension d, boolean zeroMargin) {
    Pic pic = morph.getPic();
    int sizeWorry;
    int[] dx = new int[8];
    int[] dy = new int[8];
    int[] running = new int[9];
    Point oldHere;
    Point centre;
    int extraDistance;
    int incDistance;
    Point here = new Point(d.width / 2, d.height / 2);
    
    Globals.clipBoarding = false;
    if (zeroMargin) {
      pic.margin.left = here.h;
      pic.margin.right = here.h;
      pic.margin.top = here.v;
      pic.margin.bottom = here.v;
    }
    centre = (Point) here.clone();
    plugIn(gene, dx, dy);
    pic.zeroPic(here);
    if (segNoGene < 1)
      segNoGene = 1;
    if (dGene[9] == SwellType.Swell)
      extraDistance = trickleGene;
    else if (dGene[9] == SwellType.Shrink)
      extraDistance = -trickleGene;
    else
      extraDistance = 0;

    running = gene.clone();
    incDistance = 0;
    for (int seg = 0; seg < segNoGene; seg++) {
      oddOne = seg % 2 == 1;
      if (seg > 0) {
        oldHere = (Point) here.clone();
        here.v += (segDistGene + incDistance) / trickleGene;
        incDistance += extraDistance;
        int thick;
        if (dGene[8] == SwellType.Shrink)
          thick = gene[8];
        else
          thick = 1;
        pic.picLine(oldHere.h, oldHere.v, here.h, here.v, thick, Color.BLACK);
        for (int j = 0; j < 8; j++) {
          if (dGene[j] == SwellType.Swell)
            running[j] += trickleGene;
          else if (dGene[j] == SwellType.Shrink)
            running[j] -= trickleGene;
        }
        if (running[8] < 1)
          running[8] = 1;
        plugIn(running, dx, dy);
      }
      sizeWorry = segNoGene * (1 << gene[8]);
      if (sizeWorry > Globals.worryMax)
        gene[8]--;
      if (gene[8] < 1)
        gene[8] = 1;
      tree(here.h, here.v, order, 2, dx, dy);
    }
    if (centre.h - pic.margin.left > pic.margin.right - centre.h)
      pic.margin.right = centre.h + (centre.h - pic.margin.left);
    else
      pic.margin.left = centre.h - (pic.margin.right - centre.h);

    int upExtent = centre.v - pic.margin.top; // can be zero if biomorph goes down
    int downExtent = pic.margin.bottom - centre.v;
    if (spokesGene == SpokesType.NSouth || spokesGene == SpokesType.Radial
        || Globals.theMode == ModeType.Engineering) {
      // Obscurely necessary to cope with erasing last Rect in Manipulation}
      if (upExtent > downExtent)
        pic.margin.bottom = centre.v + upExtent;
      else
        pic.margin.top = centre.v - downExtent;
    }
    if (spokesGene == SpokesType.Radial) {
      int wid = pic.margin.right - pic.margin.left;
      int ht = pic.margin.bottom - pic.margin.top;
      if (wid > ht) {
        pic.margin.top = centre.v - wid / 2 - 1;
        pic.margin.bottom = centre.v + wid / 2 + 1;
      } else {
        pic.margin.left = centre.h - ht / 2 - 1;
        pic.margin.right = centre.h + ht / 2 + 1;
      }
    }
    pic.morph = this.morph;
    if (g2 != null) {
      pic.drawPic(g2, d, centre, morph);
      g2.setColor(Color.RED);
      Rectangle rectangle = pic.margin.toRectangle();
      g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

  }



  public void makeGenes(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
    for (int j = 0; j < 10; j++) {
      dGene[j] = SwellType.Same;
    }
    segNoGene = 1;
    segDistGene = 150;
    completenessGene = CompletenessType.Double;
    spokesGene = SpokesType.NorthOnly;
    trickleGene = Biomorph.TRICKLE;
    mutSizeGene = Biomorph.TRICKLE / 2;
    mutProbGene = 10;
    gene[0] = a;
    gene[1] = b;
    gene[2] = c;
    gene[3] = d;
    gene[4] = e;
    gene[5] = f;
    gene[6] = g;
    gene[7] = h;
    gene[8] = i;
  }

  /**
   * 
   */
  public void chess() {
    makeGenes(-Biomorph.TRICKLE, 3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE,
        Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 6 * Biomorph.TRICKLE, -5 * Biomorph.TRICKLE, 7);
  }

  public void basicTree() {
    makeGenes(-10, -20, -20, -15, -15, 0, 15, 15, 7);
    segNoGene = 2;
    segDistGene = 150;
    completenessGene = CompletenessType.Single;
    dGene[3] = SwellType.Shrink;
    dGene[4] = SwellType.Shrink;
    dGene[5] = SwellType.Shrink;
    dGene[8] = SwellType.Shrink;
    trickleGene = 9;
  }

  public void insect() {
    makeGenes(Biomorph.TRICKLE, Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, Biomorph.TRICKLE,
        -Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 8 * Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, 6);
  }

public void decrementMutProbGene() {
	mutProbGene--;
	
}

public void incrementMutProbGene() {
	mutProbGene++;
	
}







}
