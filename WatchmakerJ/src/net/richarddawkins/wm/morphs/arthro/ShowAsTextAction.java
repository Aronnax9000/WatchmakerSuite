package net.richarddawkins.wm.morphs.arthro;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import net.richarddawkins.wm.BreedingPanel;

public class ShowAsTextAction extends AbstractAction {

  protected ArthromorphConfig config;
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  public static JFrame frame = null;

  public ShowAsTextAction(ArthromorphConfig config) {
    super("Show as Text");
    this.config = config;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    BreedingPanel panel = config.getBreedingPanel();
    Arthromorph morph = (Arthromorph) panel.getMorphPanels().elementAt(panel.getMidBox())
        .getMorph();
    ArthromorphGenome genome = (ArthromorphGenome) morph.getGenome();
    AtomPrinter.printMiddle(genome.getAnimalTrunk());
  }

}
