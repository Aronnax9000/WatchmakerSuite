package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class CubeAbove4RampsBelow extends AbstractColorPickerPanel {

    private static final long serialVersionUID = 1L;

    public CubeAbove4RampsBelow(SwingColorPicker swingColorPicker) {
        super(swingColorPicker);
        LayoutManager layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;

        constraints.gridy = 0;
        JPanel redPanel = new JPanel();
        redPanel.setBackground(Color.BLACK);
        redPanel.setLayout(new GridLayout(2, 3));
        add(redPanel, constraints);
        for (int r = 0; r < 6; r++) {
            JPanel greenPanel = new JPanel();
            layout = new GridLayout(6, 6);
            greenPanel.setLayout(layout);
            redPanel.add(greenPanel);
            for (int g = 0; g < 6; g++) {
                for (int b = 0; b < 6; b++) {
                    int index = r * 36 + g * 6 + b;
                    JColorLabel label = new JColorLabel(
                            this.swingColorPicker, index);
                    label.setMinimumSize(new Dimension(98, 98));
                    labels.add(label);
                    greenPanel.add(label);
                }
            }
        }
        JPanel fourRamps = new JPanel();
        layout = new GridLayout(4, 10);
        fourRamps.setLayout(layout);
        constraints.gridy++;

        add(fourRamps, constraints);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                int index = 216 + i * 10 + j;
                JColorLabel label = new JColorLabel(
                        this.swingColorPicker, index);
                // label.setMinimumSize(new Dimension(182,98));
                labels.add(label);
                fourRamps.add(label);
            }
        }
    }
}