package net.richarddawkins.watchmaker.morph.colour.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.morph.colour.geom.ColourPic;

public class ColourTestPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 7265579642948669124L;

    public ColourTestPanel() {
        setLayout(new GridLayout(16,16));
        for(int y = 0; y < 16; y++)
            for(int x = 0; x < 16; x++) {
                JPanel swatch = new JPanel();
                swatch.setBackground(ColourPic.chooseColor(y*16+x));
                add(swatch);
            }
    }
}
