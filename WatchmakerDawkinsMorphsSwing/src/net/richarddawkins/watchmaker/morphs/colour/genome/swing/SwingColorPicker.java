package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.richarddawkins.watchmaker.geom.Palette;
import net.richarddawkins.watchmaker.geom.WatchmakerColor;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColorGene;

public class SwingColorPicker extends JFrame {

    private static final long serialVersionUID = 1L;
    protected static SwingColorPicker singleton;

    public synchronized static SwingColorPicker getInstance() {
//        if (singleton == null) {
//            singleton = new SwingColorPicker();
//        }
//        return singleton;
        return new SwingColorPicker();
    }

    protected ColorGene gene;
    protected JCheckBox showValues;

    public SwingColorPicker() {
        super("Watchmaker Colors");
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        WatchmakerColor watchmakerColor = WatchmakerColor.getInstance();
        Palette palette = watchmakerColor.getPalette();
        setTitle(watchmakerColor.getPalette().getName());
        
        JPanel pickerPanel;
        if(palette.getColors().length == 256) {
            pickerPanel = new CubeAbove4RampsBelow(this);
        } else {
            pickerPanel = new CustomColorPickerPanel(this);
        }
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(pickerPanel, constraints);
        pack();
        addComponentListener(new ComponentAdapter() {
            public void componentHidden(ComponentEvent e) 
            {
                
            }
            public void componentShown(ComponentEvent e) {

            }
            });
    };

    public ColorGene getGene() {
        return gene;
    }

    public void processMouseClicked(int color) {
        gene.setValue(color);
        repaint();
    }

    public void setGene(ColorGene gene) {
        this.gene = gene;
        setTitle(gene.getName() + " " + WatchmakerColor.getInstance().getPalette().getName());
    }

}
