package net.richarddawkins.watchmaker.gui;

import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.morph.common.MorphConfig;

public class WatchmakerTabbedPane extends JTabbedPane {
	
	public WatchmakerTabbedPane() {
	    addChangeListener(new TabChangeListener());
	}
	
	protected Vector<MorphConfig> configs = new Vector<MorphConfig>();

	public void addMorphConfig(MorphConfig config) {
		configs.add(config);
		addTab(config.getName(), 
				config.getIcon(), 
				new WatchmakerPanel(
						config.getGeneBoxStrip(), 
						config.getCentrePanel()), 
						config.getToolTip());
		setSelectedIndex(this.getTabCount() - 1);
	}
	
	
    class TabChangeListener implements ChangeListener {
	    public void stateChanged(ChangeEvent e) {
	      MorphConfig config = configs.get(getSelectedIndex());
	      config.getMenuBuilder().buildMenu(config.getFrame().getJMenuBar());
	    }
   }
}
