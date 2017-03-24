package net.richarddawkins.watchmaker.swing.morphview;

import java.awt.Component;

import javax.swing.JScrollPane;

import net.richarddawkins.watchmaker.component.WatchPanel;
import net.richarddawkins.watchmaker.component.WatchScrollPane;
import net.richarddawkins.watchmaker.swing.components.SwingWatchComponent;

public class SwingWatchScrollPane extends SwingWatchComponent implements WatchScrollPane {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SwingWatchScrollPane(WatchPanel dummy) {
        component = new JScrollPane((Component)dummy.getComponent());
    }

}
