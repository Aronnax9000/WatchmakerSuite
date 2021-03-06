package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import java.awt.Color;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.morphs.colour.genome.ColorGene;
import net.richarddawkins.watchmaker.swing.SwingColor;
import net.richarddawkins.watchmaker.swing.genebox.GeneBoxType;
import net.richarddawkins.watchmaker.swing.genebox.SwingIntegerGeneBox;

public class SwingColorGeneBox extends SwingIntegerGeneBox {

    private static final long serialVersionUID = -5076715671424518452L;

    public SwingColorGeneBox(AppData appData) {
        super(appData);
        displayAsHex = true;
    }

    public void setEngineeringMode() {
        super.setEngineeringMode(GeneBoxType.clickForPicker);
    }

    public void setValue(int value) {
        Color color = SwingColor.toColor(value);
        this.setBackground(color);
        this.valueLabel.setBackground(color);
        super.setValue(value);
    }

    public void launchPicker() {
        SwingColorPicker picker = SwingColorPicker.getInstance();
        picker.setGene((ColorGene) gene);
        picker.setVisible(true);
        picker.repaint();
    }
}
