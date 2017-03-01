package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

abstract public class AbstractColorPickerPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    protected SwingColorPicker swingColorPicker;

    protected Vector<JColorLabel> labels = new Vector<JColorLabel>();

    public AbstractColorPickerPanel(SwingColorPicker swingColorPicker) {
        this.swingColorPicker = swingColorPicker;
        this.setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        int currentGeneColor = this.swingColorPicker.gene.getValue();
        for (JColorLabel label : labels) {
            Border border;
            if (label.color == currentGeneColor) {
                 border = BorderFactory.createLineBorder(Color.WHITE,1);
            } else {
                border = BorderFactory.createLineBorder(Color.BLACK, 1);
            }
            
            label.setBorder(border);
    
        }
        this.swingColorPicker.pack();
        super.paintComponent(g);
    }

}
