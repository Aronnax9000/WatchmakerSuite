package net.richarddawkins.watchmaker.morph.colour.genome;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.biomorph.Biomorph;
import net.richarddawkins.watchmaker.morph.biomorph.genome.BiomorphGenome;
import net.richarddawkins.watchmaker.morph.biomorph.genome.CompletenessType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SpokesType;
import net.richarddawkins.watchmaker.morph.biomorph.genome.SwellType;
import net.richarddawkins.watchmaker.morph.colour.ColourPic;
import net.richarddawkins.watchmaker.morph.common.geom.Pic;
import net.richarddawkins.watchmaker.morph.common.geom.Point;
import net.richarddawkins.watchmaker.morph.util.Globals;
import net.richarddawkins.watchmaker.morph.util.ModeType;

public class ColourGenome extends BiomorphGenome {
  
  public static final int RAINBOW = 256;
  
  int[] colorGene = new int[8];
  long backColorGene;
  LimbType limbShapeGene;
  LimbFillType limbFillGene;
  int thicknessGene;

  int thick;

  public int[] getColorGene() {
    return colorGene;
  }

  public void setColorGene(int[] colorGene) {
    this.colorGene = colorGene;
  }

  public long getBackColorGene() {
    return backColorGene;
  }

  public void setBackColorGene(long backColorGene) {
    this.backColorGene = backColorGene;
  }

  public LimbType getLimbShapeGene() {
    return limbShapeGene;
  }

  public void setLimbShapeGene(LimbType limbShapeGene) {
    this.limbShapeGene = limbShapeGene;
  }

  public LimbFillType getLimbFillGene() {
    return limbFillGene;
  }

  public void setLimbFillGene(LimbFillType limbFillGene) {
    this.limbFillGene = limbFillGene;
  }

  public int getThicknessGene() {
    return thicknessGene;
  }

  public void setThicknessGene(int thicknessGene) {
    this.thicknessGene = thicknessGene;
  }

  public ColourGenome(Morph morph) {
    this.morph = morph;
  }

  public Genome reproduce(Morph newMorph) {
    ColourGenome child = new ColourGenome(newMorph);
    copy(child);
    newMorph.getMorphConfig().getMutagen().mutate(child);
    return child;
  }

  @Override
  public void copy(Genome person) {
    ColourGenome child = (ColourGenome) person;
    super.copy(child);
    child.setColorGene(colorGene.clone());
    child.setBackColorGene(backColorGene);
    ;
    child.setMutSizeGene(mutSizeGene);
    child.setLimbShapeGene(limbShapeGene);
    child.setLimbFillGene(limbFillGene);
    child.setThicknessGene(thicknessGene);
  }

  void tree(int x, int y, int lgth, int dir, int[] dx, int[] dy) {
    ColourPic pic = (ColourPic) morph.getPic();
    int xnew;
    int ynew;
    if (dir < 0) {
      dir += 8;
    }
    if (dir >= 8) {
      dir -= 8;
    }
    xnew = x + lgth * dx[dir] / trickleGene;
    ynew = y + lgth * dy[dir] / trickleGene;
    pic.margin.left = Math.min(pic.margin.left, x);
    pic.margin.right = Math.max(pic.margin.right, x + thicknessGene);
    pic.margin.bottom = Math.max(pic.margin.bottom, y + thicknessGene);
    pic.margin.top = Math.min(pic.margin.top, y);
    pic.margin.left = Math.min(pic.margin.left, xnew);
    pic.margin.right = Math.max(pic.margin.right, xnew);
    pic.margin.bottom = Math.max(pic.margin.bottom, ynew);
    pic.margin.top = Math.min(pic.margin.top, ynew);
    int subscript = (gene[8] - lgth) % 8; // + 1; Trimmed off + 1 to make it
    // zero based.
    pic.picLine(x, y, xnew, ynew, 1, ColourPic.chooseColor(colorGene[subscript]));
    if (lgth > 1) {
      if (oddOne) {
        tree(xnew, ynew, lgth - 1, dir + 1, dx, dy);
        if (lgth < order) {
          tree(xnew, ynew, lgth - 1, dir - 1, dx, dy);
        }
      } else {
        tree(xnew, ynew, lgth - 1, dir - 1, dx, dy);
        if (lgth < order) {
          tree(xnew, ynew, lgth - 1, dir + 1, dx, dy);
        }
      }
    }
  }

  public void develop(Graphics2D g2, Dimension d, boolean zeroMargin) {
    Point here = new Point(d.width / 2, d.height / 2);

    Pic pic = this.morph.getPic();
    // int x;
    // int y;
    int upExtent;
    int downExtent;
    int wid;
    int ht;
    int[] dx = new int[8];
    int[] dy = new int[8];
    int[] running = new int[9];
    Point oldHere;
    Point centre;
    // boolean oddOne;
    int extraDistance;
    int incDistance;
    int dummyColour;
    if (zeroMargin) {
      pic.margin.left = here.h;
      pic.margin.right = here.h;
      pic.margin.top = here.v;
      pic.margin.bottom = here.v;
    }
    centre = (Point) here.clone();
    plugIn(gene, dx, dy);
    pic.zeroPic(here);
    if (segNoGene < 1) {
      segNoGene = 1;
    }
    if (dGene[9] == SwellType.Swell) {
      extraDistance = trickleGene;
    } else if (dGene[9] == SwellType.Shrink) {
      extraDistance = -trickleGene;
    } else {
      extraDistance = 0;
    }
    running = gene.clone();
    incDistance = 0;
    for (int seg = 0; seg < segNoGene; seg++) {
      oddOne = (seg & 1) == 1;
      if (seg > 0) {
        oldHere = (Point) here.clone();
        here.v += (segDistGene + incDistance) / trickleGene;
        incDistance += extraDistance;
        dummyColour = 100;
        pic.picLine(oldHere.h, oldHere.v, here.h, here.v, 1, ColourPic.chooseColor(dummyColour));
        for (int j = 0; j < 8; j++) {
          if (dGene[j] == SwellType.Swell) {
            running[j] += trickleGene;
          }
          if (dGene[j] == SwellType.Shrink) {
            running[j] -= trickleGene;
          }
        }
        if (running[8] < 1) {
          running[8] = 1;
        }
        plugIn(running, dx, dy);
      }
      tree(here.h, here.v, order, 2, dx, dy);
    }
    if (centre.h - pic.margin.left > pic.margin.right - centre.h) {
      pic.margin.right = centre.h + (centre.h - pic.margin.left);
    } else {
      pic.margin.left = centre.h - (pic.margin.right - centre.h);
    }
    upExtent = centre.v - pic.margin.top;
    downExtent = pic.margin.bottom - centre.v;
    if (spokesGene == SpokesType.NSouth || spokesGene == SpokesType.Radial
        || Globals.theMode == ModeType.Engineering) {
      // {Obscurely necessary to cope with erasing last rect in
      // Manipulation}
      if (upExtent > downExtent) {
        pic.margin.bottom = centre.v + upExtent;
      } else {
        pic.margin.top = centre.v - downExtent;
      }
    }
    if (spokesGene == SpokesType.Radial) {
      wid = pic.margin.right - pic.margin.left;
      ht = pic.margin.bottom - pic.margin.top;
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
      if(morph.getMorphConfig().isShowBoundingBoxes()) {
        g2.setColor(Color.RED);
        Rectangle rectangle = pic.margin.toRectangle();
        g2.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
      }
    }
  }

  public void makeGenes(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
    for (int j = 0; j < 10; j++) {
      dGene[j] = SwellType.Same;
    }
    segNoGene = 1;
    segDistGene = 1;
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

  public void addToBackColorGene(int summand) {
    backColorGene += summand;
    if (backColorGene > RAINBOW - 1) {
      backColorGene = RAINBOW - 1;
    }
    if (backColorGene < 0) {
      backColorGene = 0;
    }
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

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#chess()
   */
  @Override
  public void chess() {

    makeGenes(-Biomorph.TRICKLE, 3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE, -3 * Biomorph.TRICKLE,
        Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 6 * Biomorph.TRICKLE, -5 * Biomorph.TRICKLE, 7);
    for (int j = 0; j < 8; j++) {
      colorGene[j] = RAINBOW / 2;
    }
    backColorGene = RAINBOW / 3;
    limbShapeGene = LimbType.Stick;
    limbFillGene = LimbFillType.Filled;
    thicknessGene = 1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#insect()
   */
  @Override
  public void insect() {
    makeGenes(Biomorph.TRICKLE, Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, Biomorph.TRICKLE,
        -Biomorph.TRICKLE, -2 * Biomorph.TRICKLE, 8 * Biomorph.TRICKLE, -4 * Biomorph.TRICKLE, 6);
    for (int j = 0; j < 8; j++) {
      colorGene[j] = RAINBOW / 2;
    }
    backColorGene = RAINBOW / 3;
    limbShapeGene = LimbType.Stick;
    limbFillGene = LimbFillType.Filled;
    thicknessGene = 1;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.richarddawkins.watchmaker.morphs.colour.impl.ColourBiomorph#basicTree ()
   */
  @Override
  public void basicTree() {
    makeGenes(-Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE, -Biomorph.TRICKLE,
        -Biomorph.TRICKLE, 0, Biomorph.TRICKLE, Biomorph.TRICKLE, 6);
    for (int j = 0; j < 8; j++) {
      colorGene[j] = RAINBOW / 2;
    }
    backColorGene = RAINBOW / 3;
    limbShapeGene = LimbType.Stick;
    limbFillGene = LimbFillType.Filled;
    thicknessGene = 1;
  }


  /**
   * Doesn't allow thicknessGene to fall below 1.
   * @param summand the quantity to add to the ThicknessGene
   */
  public void addToThicknessGene(int summand) {
    thicknessGene += summand;
    if (thicknessGene < 1) {
      thicknessGene = 1;
    }

  }

  public void addToColorGene(int j, int summand) {
    colorGene[j] += summand;
    if (colorGene[j] > RAINBOW) {
      colorGene[j] = RAINBOW;
    }
    if (colorGene[j] < 0) {
      colorGene[j] = 0;
    }
  }

}
