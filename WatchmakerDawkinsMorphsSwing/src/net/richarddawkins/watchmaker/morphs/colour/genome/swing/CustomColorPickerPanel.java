package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import java.awt.Dimension;
import java.awt.GridLayout;

import net.richarddawkins.watchmaker.geom.WatchmakerColor;

public class CustomColorPickerPanel extends AbstractColorPickerPanel {


    public CustomColorPickerPanel(SwingColorPicker swingColorPicker) {
        super(swingColorPicker);
        GridLayout layout = new GridLayout(0,16);
        setLayout(layout);
        for (int index = 0; index < WatchmakerColor.getInstance().getPalette().getColors().length; index++) {
            JColorLabel label = new JColorLabel(
                    this.swingColorPicker, index);
            label.setMinimumSize(new Dimension(98, 98));
            labels.add(label);
            add(label);
        }
    }
    
    private static final long serialVersionUID = 1L;

}
