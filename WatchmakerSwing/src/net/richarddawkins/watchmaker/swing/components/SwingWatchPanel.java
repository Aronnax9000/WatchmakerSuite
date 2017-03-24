package net.richarddawkins.watchmaker.swing.components;

import javax.swing.JPanel;

import net.richarddawkins.watchmaker.component.WatchPanel;

public class SwingWatchPanel extends SwingWatchComponent implements WatchPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SwingWatchPanel() {
        component = new JPanel();
    }

}
