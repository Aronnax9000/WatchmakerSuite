package net.richarddawkins.watchmaker.gui.genebox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.mono.CompletenessType;
import net.richarddawkins.watchmaker.morph.mono.SpokesType;
import net.richarddawkins.watchmaker.morph.mono.SwellType;
import net.richarddawkins.watchmaker.resourceloader.Messages;
import net.richarddawkins.watchmaker.resourceloader.WatchmakerCursors;

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
  public CompletenessType isCompleteness() {
    return completeness;
  }

  
  public void setCompleteness(CompletenessType completeness) {
    this.completeness = completeness;
    String labelString = "";
    switch(completeness) {
    case Single:
    	  labelString = Messages.getMessages().getString("STRO_12947,0");
    	  break;
    case Double:
    	  labelString = Messages.getMessages().getString("STRO_12947,1");
    	  break;
    }
    this.valueLabel.setText(labelString);
  }

  public SpokesType getSpokesType() {
    return spokes;
  }


  public void setSpokes(SpokesType spokes) {
    this.spokes = spokes;
    
    String labelString = "";
    
    switch(spokes) {
    case NorthOnly:
  	  labelString = Messages.getMessages().getString("STRO_12947,2");
  	  break;
  	  //        DrawString(SingleString);
    case NSouth:
    	  labelString = Messages.getMessages().getString("STRO_12947,3");
//        DrawString(UpDnString);
    	  break;
    case Radial:
    	  labelString = Messages.getMessages().getString("STRO_12947,4");
//        DrawString(RadialString);
    	  break;
    }
    this.valueLabel.setText(labelString);
    
  }


  public SwellType getSwell() {
    return swell;
  }


  public void setSwell(SwellType swell) {
    this.gradientPanel.setSwell(swell);
  }


  public boolean isNegative() {
    return negative;
  }


  public void setNegative(boolean negative) {
    this.negative = negative;
  }

  public boolean isHasCompleteness() {
	return hasCompleteness;
}


public void setHasCompleteness(boolean hasCompleteness) {
	this.hasCompleteness = hasCompleteness;
}


public boolean isHasSpokes() {
	return hasSpokes;
}


public void setHasSpokes(boolean hasSpokes) {
	this.hasSpokes = hasSpokes;
}


public boolean isHasSwell() {
	return hasSwell;
}


public void setHasSwell(boolean hasSwell) {
	this.hasSwell = hasSwell;
}

protected boolean hasCompleteness = false;
  protected boolean hasSpokes = false;
  protected boolean hasSwell = false;
  
  protected CompletenessType completeness;
  protected SpokesType spokes;
  protected SwellType swell;
  protected boolean negative;
  
  

  protected GradientPanel gradientPanel = new GradientPanel();
  protected JPanel valuePanel = new JPanel();
  protected JLabel valueLabel = new JLabel("X");
  enum HorizPos {LeftThird, MidThird, RightThird}
  enum VertPos {TopRung, MidRung, BottomRung}
  public static int GenesHeight = 20;

  public void setValueLabelValue(int value) {
	  valueLabel.setText(new Integer(value).toString());
  }
  
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
