package net.richarddawkins.watchmaker.morphs.mono;



import net.richarddawkins.watchmaker.geom.Point;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morphs.Pic;
import net.richarddawkins.watchmaker.morphs.biomorph.SpokesType;
import net.richarddawkins.watchmaker.morphs.biomorph.SwellType;

public class MonochromeBiomorphDeveloperImpl {

  int order;
  int x, y, seg, upExtent, downExtent, wid, ht, sizeWorry, thick;
  int[] dx = new int[8];
  int[] dy = new int[8];
  int[] running = new int[9];
  Point oldHere, centre;
  boolean oddOne;
  int extraDistance, incDistance;
  MonochromePerson biomorph;
  
  //Globals
  static boolean clipBoarding;
  static boolean zeroMargin;
  static Rect margin;
  static int worryMax = 4095;
  static ModeType mode;
  static Pic myPic;
  static boolean delayedDrawing;
  
  void tree(int x, int y, int lgth, int dir) {
    int xnew, ynew;
    if(dir < 0) 
      dir += 8;
    if(dir >= 8) 
      dir -= 8;
    if( biomorph.getTrickleGene() < 1) 
      biomorph.setTrickleGene(1);
    xnew = x + lgth * dx[dir] / biomorph.getTrickleGene();
    ynew = y + lgth * dy[dir] / biomorph.getTrickleGene();
    if(x < margin.left)
      margin.left = x;
    if(x > margin.right)
      margin.right = x;
    if(y > margin.bottom)
      margin.bottom = y;
    if(y < margin.top)
      margin.top = y;
    if(xnew < margin.left)
      margin.left = xnew;
    if(xnew > margin.right)
      margin.right = xnew;
    if(ynew > margin.bottom)
      margin.bottom = ynew;
    if(ynew < margin.top)
      margin.top = ynew;

    if( biomorph.getDGene(8) == SwellType.Shrink)
      thick = lgth;
    else if( biomorph.getDGene(8) == SwellType.Swell)
      thick = 1 + biomorph.getGene(9) - lgth;
    else
    thick = 1;
//    picLine(MyPic, x, y, xnew, ynew, thick * MyPenSize);
    if (lgth > 1) {
        if(oddOne) {

            tree(xnew, ynew, lgth - 1, dir + 1);
            if(lgth < order) 
              tree(xnew, ynew, lgth - 1, dir - 1);
        } else {
            tree(xnew, ynew, lgth - 1, dir - 1);
            if(lgth < order) {
              tree(xnew, ynew, lgth - 1, dir + 1);
            }
        }
    }
  }
    
  void plugIn(int[] gene) {
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
  
  public MonochromeBiomorphDeveloperImpl() { }

  void develop(MonochromePerson biomorph, Point here) {
    
    this.biomorph = (MonochromePerson) biomorph;
    
    clipBoarding = false;
  
    if(zeroMargin) {
        margin.left = here.h;
        margin.right = here.h;
        margin.right = here.h;
        margin.top = here.v;
        margin.bottom = here.v;
    }
    
    centre = here.clone();

    plugIn(biomorph.getGene());
    zeroPic(myPic, here);

    if(biomorph.getSegNoGene() < 1)
      biomorph.setSegNoGene(1);
    if(biomorph.getDGene(9) == SwellType.Swell)
      extraDistance = biomorph.getTrickleGene();
    else if(biomorph.getDGene(9) == SwellType.Shrink)
      extraDistance = - biomorph.getTrickleGene();
    else
      extraDistance = 0;
    running = biomorph.getGene();
    incDistance = 0;
    for(seg = 0; seg < biomorph.getSegNoGene(); seg++) {
        oddOne = seg % 2 == 1; // odd(seg);
        if(seg > 1) {
            oldHere = here.clone();
            here.v += (biomorph.getSegDistGene() + incDistance) / biomorph.getTrickleGene();
            incDistance += extraDistance;
            if(biomorph.getDGene(8) == SwellType.Shrink)
              thick = biomorph.getGene(8);
            else
              thick = 1;
            picLine(myPic, oldHere.h, oldHere.v, here.h, here.v, thick);
            for(int j = 0; j < 8; j++) {
                if(biomorph.getDGene(j) == SwellType.Swell) 
                  running[j] += biomorph.getTrickleGene();
                if(biomorph.getDGene(j) == SwellType.Shrink)
                  running[j] -= biomorph.getTrickleGene();
            }
            if(running[8] < 1)
              running[8] = 1;
            plugIn(running);
        }
        sizeWorry = (int) (biomorph.getSegNoGene() * Math.pow(2, biomorph.getGene(8)));
        if(sizeWorry > worryMax) 
          biomorph.decrementGene(8);
        if(biomorph.getGene(8) < 1)
          biomorph.setGene(8, 1);
        tree(here.h, here.v, order, 2);
    }
    if(centre.h - margin.left > margin.right - centre.h)
      margin.right = centre.h + (centre.h - margin.left);
    else
      margin.left = centre.h - (margin.right - centre.h);
    upExtent = centre.v - margin.top; // can be zero if biomorph goes down
    downExtent = margin.bottom - centre.v;
    // Obscurely necessary to cope with erasing last Rect in Manipulation
    if(biomorph.getSpokesGene() == SpokesType.NSouth 
        || biomorph.getSpokesGene() == SpokesType.Radial 
        || mode == ModeType.Engineering) {
        if(upExtent > downExtent)
          margin.bottom = centre.v + upExtent;
        else
          margin.top = centre.v - downExtent;
    }
    if(biomorph.getSpokesGene() == SpokesType.Radial) {
        wid = margin.right - margin.left;
        ht = margin.bottom - margin.top;
        if(wid > ht) {
          margin.top = centre.v - wid / 2 - 1;
          margin.bottom = centre.v + wid / 2 + 1;
        } else {
          margin.left = centre.h - ht / 2 - 1;
          margin.right = centre.h + ht / 2 + 1;
        }
    }
    myPic.morph = biomorph.getMorph();
    if(! delayedDrawing)
      drawPic(myPic, centre, biomorph);
  }


  private void zeroPic(Pic thisPic, Point here) {
    thisPic.lines.removeAllElements();
//    thisPic.origin = here; FIXME
  }


  private void picLine(Pic myPic2, int h, int v, int h2, int v2, int thick2) {
    // TODO Auto-generated method stub
    
  }


  private void drawPic(Pic myPic2, Point centre2, MonochromePerson biomorph2) {
    // TODO Auto-generated method stub
    
  }
  
}
