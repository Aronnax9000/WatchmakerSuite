package net.richarddawkins.watchmaker.gui.old;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.common.Morph;

public class MorphPanel extends JPanel {

  protected BreedingPanelOld breedingPanelOld;
  /**
   * 
   */
  protected boolean midBox;

  public boolean isMidBox() {
    return midBox;
  }

  private static final long serialVersionUID = 1L;

  protected Morph morph = null;

  public Morph getMorph() {
    return morph;
  }

  public void setMorph(Morph morph) {
    this.morph = morph;
  }

  public MorphPanel(BreedingPanelOld parent, boolean midBox) {
    this.breedingPanelOld = parent;
    this.midBox = midBox;
    addMouseListener(new MouseListener() {

      @Override
      public void mouseClicked(MouseEvent arg0) {
        breedingPanelOld.populate(getMorph());
      }

      @Override
      public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mousePressed(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }

      @Override
      public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub

      }
    });
    setBorder(BorderFactory.createLineBorder(Color.black));
    setPreferredSize(new Dimension(200, 200));
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    int width = getWidth();
    int height = getHeight();
    g2.setColor(Color.WHITE);
    g2.fillRect(0, 0, width, height);
    if (morph != null)
      morph.draw(g2, this.getSize(), midBox);
  }

}
