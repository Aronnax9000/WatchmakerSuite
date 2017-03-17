package net.richarddawkins.watchmaker.swing.components;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.component.WatchPanel;

public class SwingWatchPanel extends SwingWatchComponent implements WatchPanel {
    public SwingWatchPanel() {
        component = new JPanel();
    }

}
