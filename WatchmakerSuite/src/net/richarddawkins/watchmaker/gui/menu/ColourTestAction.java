package net.richarddawkins.watchmaker.gui.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;

import net.richarddawkins.watchmaker.morph.colour.gui.ColourTestPanel;

public class ColourTestAction extends AbstractAction {
    /**
     * 
     */
    private static final long serialVersionUID = 9199450806374346556L;
    JTabbedPane pane;
    public ColourTestAction(JTabbedPane pane) {
        super("Colour Test");
        this.pane = pane;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        pane.add("Colour Test", new ColourTestPanel());
        
    }


}
