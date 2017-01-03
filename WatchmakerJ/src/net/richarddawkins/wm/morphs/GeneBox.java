package net.richarddawkins.watchmaker.morphs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.WatchmakerCursors;

public class GeneBox extends JPanel {
 


  

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   * @return true if the GeneBox shadows a Completeness (Double, Single) type
   * gene, false otherwise.
   */
  public boolean isCompleteness() {
    return completeness;
  }


  public void setCompleteness(boolean completeness) {
    this.completeness = completeness;
  }


  public boolean isSpokes() {
    return spokes;
  }


  public void setSpokes(boolean spokes) {
    this.spokes = spokes;
  }


  public boolean isSwell() {
    return swell;
  }


  public void setSwell(boolean swell) {
    this.swell = swell;
  }


  public boolean isNegative() {
    return negative;
  }


  public void setNegative(boolean negative) {
    this.negative = negative;
  }


  protected boolean completeness;
  protected boolean spokes;
  protected boolean swell;
  protected boolean negative;
  
  
  
  
  protected JPanel eastPanel = new JPanel();
  protected JPanel gradientPanel = new JPanel();
  protected JPanel valuePanel = new JPanel();
  protected JLabel valueLabel = new JLabel("X");
  enum HorizPos {LeftThird, MidThird, RightThird}
  enum VertPos {TopRung, MidRung, BottomRung}
  public static int GenesHeight = 20;

  
  public GeneBox(Component c, int numberSharingStrip) {
    
    
    this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    this.setLayout(new BorderLayout());
    this.add(gradientPanel, BorderLayout.WEST);
    this.add(valuePanel, BorderLayout.CENTER);
    valuePanel.add(valueLabel);
    this.addMouseMotionListener(new MouseMotionListener() {
      @Override
      public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
      }
      @Override
      public void mouseMoved(MouseEvent e) {
        
        HorizPos mouseHoriz;
        VertPos mouseVert;
        int thirdWidth = e.getComponent().getWidth() / 3;
        int thirdHeight = e.getComponent().getHeight() / 3;
        int x = e.getX();
        int y = e.getY();
        
        if(x < thirdWidth) mouseHoriz = HorizPos.LeftThird;
        else if(x < 2 * thirdWidth) mouseHoriz = HorizPos.MidThird;
        else mouseHoriz = HorizPos.RightThird;
        
        if(y < thirdHeight) mouseVert = VertPos.TopRung;
        else if(y < 2 * thirdHeight) mouseVert = VertPos.MidRung;
        else mouseVert = VertPos.BottomRung;
        
        switch(mouseHoriz) {
        case LeftThird: 
          setCursor(WatchmakerCursors.leftArrow);
          break;
        case RightThird: 
          setCursor(WatchmakerCursors.rightArrow);
          break;
        case MidThird:
          switch(mouseVert) {
//          IF (j <= 9) OR (j = 11) THEN
          case TopRung: 
            setCursor(WatchmakerCursors.upArrow);
            break;
          case MidRung: 
            setCursor(WatchmakerCursors.equalsArrow);
            break;
          case BottomRung: 
            setCursor(WatchmakerCursors.downArrow);
            break;
          }
//          ELSE
//            SetCursor(CursList[EqCursor]^^);
        }
      }});
    }
  
}
