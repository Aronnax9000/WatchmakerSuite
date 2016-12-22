package net.richarddawkins.watchmaker.morph.mono;

import java.awt.Color;

import net.richarddawkins.watchmaker.morph.common.Biomorph;
import net.richarddawkins.watchmaker.morph.common.BiomorphGenomeImpl;
import net.richarddawkins.watchmaker.morph.common.Genome;
import net.richarddawkins.watchmaker.morph.common.Morph;
import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.common.Mutagen;
import net.richarddawkins.watchmaker.morph.common.geom.Pic;
import net.richarddawkins.watchmaker.morph.common.geom.Point;
import net.richarddawkins.watchmaker.morph.util.Globals;

public class MonochromeGenome extends BiomorphGenomeImpl {

  MonochromeGenome(Morph morph) {
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

}
