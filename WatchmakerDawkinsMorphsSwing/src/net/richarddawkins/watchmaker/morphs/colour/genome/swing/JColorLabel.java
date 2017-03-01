package net.richarddawkins.watchmaker.morphs.colour.genome.swing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.swing.SwingColor;

public class JColorLabel extends JPanel {

    private final SwingColorPicker swingColorPicker;
    private static final long serialVersionUID = 1L;
    protected int color;

    public JColorLabel(final SwingColorPicker swingColorPicker, int color) {
        this.swingColorPicker = swingColorPicker;
        this.color = color;
        this.setBackground(SwingColor.toColor(color));
        this.setOpaque(true);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                swingColorPicker.processMouseClicked(
                        ((JColorLabel) e.getSource()).color);
            }
        });
    }
}