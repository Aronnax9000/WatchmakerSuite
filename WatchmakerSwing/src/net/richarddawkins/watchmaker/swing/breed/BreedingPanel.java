package net.richarddawkins.watchmaker.swing.breed;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Timer;

import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Boxes;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.swing.components.SwingBorderLayoutMorphView;
import net.richarddawkins.watchmaker.swing.morphview.BoxyMorphViewPanel;

public class BreedingPanel extends BoxyMorphViewPanel implements ActionListener {

    enum Phase {
        animate_mother, breed_complete, draw_out_offspring, mouse_clicked, reactivate_grid
    }

    private static final long serialVersionUID = 8668997028542499649L;
    protected BoxedMorph boxedMorphSpecial;

    protected MouseAdapter mouseAdapter;;

    private BoxedMorph newestOffspring = null;
    public Phase phase = Phase.breed_complete;
    public int special = -1;
    Timer timer = new Timer(1000 / 60, this);

    private int vacantBoxNumber = -1;

    protected SwingBorderLayoutMorphView watchmakerPanel;

    public BreedingPanel(SwingBorderLayoutMorphView watchmakerPanel) {
        super(watchmakerPanel.getSwingAppData());
        this.watchmakerPanel = watchmakerPanel;
        MorphConfig config = watchmakerPanel.getSwingAppData().getMorphConfig();
        boxes = new Boxes(config.getDefaultBreedingCols(), config.getDefaultBreedingRows());
        mouseAdapter = new BreedingPanelMouseAdapter(this);
        this.addMouseMotionListener(new BreedingPanelMouseMotionAdapter(this));
        this.addMouseListener(mouseAdapter);
        setPreferredSize(new Dimension(640, 480));
        setBorder(BorderFactory.createLineBorder(Color.black));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

    public SwingBorderLayoutMorphView getWatchmakerPanel() {
        return watchmakerPanel;
    }

    public void seed(Morph morph) {

        Morph parent;
        if (morph == null)
            parent = watchmakerPanel.getSwingAppData().getMorphConfig().createMorph(1);
        else
            parent = morph;
        BoxedMorph boxedMorph = new BoxedMorph(parent, boxes.midBox);
        boxedMorphVector.add(boxedMorph);
        GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getUpperStrip();
        geneBoxStrip.setGenome(parent.getGenome());
        // Trigger first breeding
        special = boxes.midBox;
        phase = Phase.mouse_clicked;

    }

    public void setWatchmakerPanel(SwingBorderLayoutMorphView watchmakerPanel) {
        this.watchmakerPanel = watchmakerPanel;
    }

    protected void updateModel(Dimension size) {
        Vector<Point> mids = boxes.getMidPoints(size);

        switch (phase) {
        case breed_complete:
            special = -1;
            boxedMorphSpecial = null;
            timer.stop();

            for (BoxedMorph boxedMorph : boxedMorphVector.getBoxedMorphs()) {
                boxedMorph.setPosition(boxes.getMidPoint(size, boxedMorph.boxNo));
            }

            GeneBoxStrip geneBoxStrip = (GeneBoxStrip) watchmakerPanel.getUpperStrip();
            geneBoxStrip.setGenome(boxedMorphVector.getBoxedMorph(boxes.midBox).getMorph().getGenome());
            break;
        case mouse_clicked:
            boxedMorphSpecial = boxedMorphVector.getBoxedMorph(special);
            boxedMorphVector.removeAllElements();
            boxedMorphVector.add(boxedMorphSpecial);
            Point midPoint = boxes.getMidPoint(size, special);
            boxedMorphSpecial.setPosition(midPoint);
            boxedMorphSpecial.setDestination(mids.elementAt(boxes.midBox));
            boxedMorphSpecial.setProgress(0.0d);
            boxedMorphSpecial.setScaleWithProgress(false);
            showBoxes = false;
            phase = Phase.animate_mother;
            timer.start();
            break;
        case animate_mother:
            boxedMorphSpecial.setPosition(mids.elementAt(special));
            boxedMorphSpecial.setDestination(mids.elementAt(boxes.midBox));
            boxedMorphSpecial.nudge();
            if (boxedMorphSpecial.getProgress() == 1.0d)
                phase = Phase.reactivate_grid;
            break;
        case reactivate_grid:
            timer.stop();
            boxedMorphSpecial.setDestination(null);
            boxedMorphSpecial.setProgress(0.0d);
            boxedMorphSpecial.setBoxNo(boxes.midBox);
            boxedMorphSpecial.setPosition(boxes.getMidPoint(size, boxes.midBox));
            showBoxes = true;
            vacantBoxNumber = 0;
            phase = Phase.draw_out_offspring;
            timer.start();
            break;
        case draw_out_offspring:
            if (newestOffspring != null) {
                newestOffspring.nudge();
                if (newestOffspring.getProgress() == 1.0d) {
                    newestOffspring.setPosition(newestOffspring.getDestination());
                    newestOffspring.setDestination(null);
                    newestOffspring.setProgress(0.0d);
                    newestOffspring = null;
                }
                break;
            }
            if (vacantBoxNumber == boxes.boxCount) {
                phase = Phase.breed_complete;
                timer.restart();
                break;
            }

            Morph babyMorph = boxedMorphSpecial.getMorph().reproduce();
            newestOffspring = new BoxedMorph(babyMorph, vacantBoxNumber);
            newestOffspring.setPosition(boxedMorphSpecial.getPosition());
            newestOffspring.setDestination(mids.elementAt(vacantBoxNumber));
            newestOffspring.setProgress(0.0d);
            newestOffspring.setScaleWithProgress(true);
            boxedMorphVector.add(newestOffspring);

            vacantBoxNumber++;
            // Skip center box number (already occupied by mother)
            if (vacantBoxNumber == boxes.midBox)
                vacantBoxNumber++;

            timer.restart();
            break;

        default:
        }

    }

}
