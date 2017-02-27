package net.richarddawkins.watchmaker.swing.geneboxstrip;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.genebox.GeneBox;
import net.richarddawkins.watchmaker.genebox.GeneBoxStrip;
import net.richarddawkins.watchmaker.genome.Gene;
import net.richarddawkins.watchmaker.genome.Genome;
import net.richarddawkins.watchmaker.genome.IntegerGene;
import net.richarddawkins.watchmaker.geom.BoxedMorph;
import net.richarddawkins.watchmaker.geom.Rect;
import net.richarddawkins.watchmaker.morph.Morph;
import net.richarddawkins.watchmaker.morph.draw.BoxedMorphCollection;
import net.richarddawkins.watchmaker.swing.genebox.SwingIntegerGeneBox;
import net.richarddawkins.watchmaker.swing.morphview.SwingMorphViewPanel;

public abstract class SwingGeneBoxStrip extends JPanel
        implements GeneBoxStrip, PropertyChangeListener {

    private static Logger logger = Logger.getLogger(
            "net.richarddawkins.watchmaker.swing.geneboxstrip.SwingGeneBoxStrip");

    @Override
    public void paintComponent(Graphics g) {
        logger.fine("SwingGeneBoxStrip.paintComponent() Genome:" + genome);
        super.paintComponent(g);
    }

    protected JPanel panel = (JPanel) this;

    protected boolean geneBoxToSide = false;

    public boolean isGeneBoxToSide() {
        return geneBoxToSide;
    }

    public void setGeneBoxToSide(boolean geneBoxToSide) {
        this.geneBoxToSide = geneBoxToSide;
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void setPanel(Object newValue) {
        this.panel = (JPanel) newValue;
    }

    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    protected Genome genome;

    private static final long serialVersionUID = 1L;

    public SwingGeneBoxStrip() {
        // this.setBorder(new LineBorder(Color.GREEN));
    }

    @Override
    public GeneBox getGeneBoxForGene(Gene gene) {
        if (gene instanceof IntegerGene)
            return new SwingIntegerGeneBox();
        else {
            return null;
        }
    }

    public void setGeneBoxCount(int geneBoxCount) {
        this.setLayout(new GridLayout(1, geneBoxCount));

    }

    protected boolean engineeringMode;

    public void setEngineeringMode(boolean newValue) {
        this.engineeringMode = newValue;
    }

    public void setGenome(Genome newGenome) {
        logger.fine("SwingGeneBoxStrip.setGenome(" + newGenome + ")");
        boolean reusing = true;
        if (this.getComponentCount() == 0 || !isReusable()) {
            reusing = false;
        }
        if (!reusing || newGenome == null) {
            this.removeAll();
        }
        if (newGenome != null) {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.weightx = 1;
            constraints.weighty = 0;
            constraints.fill = GridBagConstraints.HORIZONTAL;
            int n = 0;
            for (Gene gene : newGenome.toGeneArray()) {

                GeneBox geneBox;
                if (reusing) {
                    geneBox = (GeneBox) this.getComponent(n++);
                } else {
                    geneBox = (GeneBox) getGeneBoxForGene(gene);
                }
                if (engineeringMode) {
                    geneBox.setEngineeringMode();
                    geneBox.setGene(gene);
                }
                applyGeneSpecificConstraints(constraints, gene);
                if (!reusing) {
                    add((Component) geneBox, constraints);
                    if (this.geneBoxToSide) {
                        constraints.gridy++;
                    } else {
                        constraints.gridx++;
                    }
                }
            }
        } else {
            logger.warning("SwingGeneBoxStrip: new genome is null.");
        }

        this.genome = newGenome;
        if (genome != null) {
            Component[] components = this.getComponents();
            Gene[] genes = genome.toGeneArray();
            for (int i = 0; i < components.length; i++) {
                GeneBox geneBox = (GeneBox) components[i];
                geneBox.setGene(genes[i]);
            }
        }
        revalidate();
        repaint();
    }

    protected void applyGeneSpecificConstraints(GridBagConstraints constraints,
            Gene gene) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("selectedBox")) {
            SwingMorphViewPanel panel = (SwingMorphViewPanel) evt.getSource();
            BoxedMorph boxedMorph = panel.getBoxedMorphCollection()
                    .getBoxedMorph((Rect) evt.getNewValue());
            if(boxedMorph != null) {
                Morph morph = boxedMorph.getMorph();
                setGenome(morph.getGenome());
            } else {
                setGenome(null);
            }
        } else if (evt.getPropertyName().equals("boxedMorphCollection")) {
            setGenome(((BoxedMorphCollection) evt.getNewValue()).firstElement()
                    .getMorph().getGenome());
        }
    }
}
