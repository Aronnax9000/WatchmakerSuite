package net.richarddawkins.watchmaker.gui;

import java.util.Vector;

import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.morph.common.MorphConfig;
import net.richarddawkins.watchmaker.morph.common.MorphConfigFactory;
import net.richarddawkins.watchmaker.morph.common.MorphType;
import net.richarddawkins.watchmaker.morph.common.MorphTypeNotSupportedException;

public class WatchmakerTabbedPane extends JTabbedPane {
	
	
	protected JMenuBar jMenuBar;

	private static final long serialVersionUID = -9105080336982806166L;

	public WatchmakerTabbedPane(JMenuBar jMenuBar) {
		super();
		this.jMenuBar = jMenuBar;	
    	for (MorphType morphType : MorphType.values()) {
    		System.out.println("Creating WatchmakerTabbedPane for " + morphType.toString());
    		MorphConfigFactory factory = MorphConfigFactory.getInstance(morphType); 
	    	if(factory != null) {
	    		try {
			 	    	MorphConfig config = factory.createConfig();
			 	    	config.setFrame(this);
			 	    	addMorphConfig(config);
	    		}
	    		catch(MorphTypeNotSupportedException e) {
	    			// Just go on to the next MorphType
	    		}
		    }	   
    	}
    	if(this.getTabCount() != 0)
    		changeToTab(0);
		addChangeListener(new TabChangeListener());
	    
	}
	
	protected Vector<MorphConfig> configs = new Vector<MorphConfig>();

	public void addMorphConfig(MorphConfig config) {
		System.out.println("addMorphConfig " + config.getName());
		configs.add(config);
		MorphViewsTabbedPane morphViews = new MorphViewsTabbedPane(config);
		config.addDefaultMorphView(morphViews);
		addTab(config.getName(), 
				config.getIcon(), 
				morphViews,
				config.getToolTip());
//		setSelectedIndex(this.getTabCount() - 1);
	}
	
	public void changeToTab(int selectedIndex) {
	      MorphConfig config = configs.get(selectedIndex);
	      config.getMenuBuilder().buildMenu(jMenuBar);
	}
	
    class TabChangeListener implements ChangeListener {
	    public void stateChanged(ChangeEvent e) {
	    	changeToTab(getSelectedIndex());
	    }
   }
}
