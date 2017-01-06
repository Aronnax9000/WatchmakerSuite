package net.richarddawkins.watchmaker.gui;

import java.util.Vector;

import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.morph.MorphConfig;
import net.richarddawkins.watchmaker.morph.MorphConfigFactory;
import net.richarddawkins.watchmaker.morph.MorphType;
import net.richarddawkins.watchmaker.morph.MorphTypeNotSupportedException;

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
	    setSelectedIndex(0);
	}
	
	protected Vector<MorphConfig> configs = new Vector<MorphConfig>();
	public String uniquify(String name) {
		String newName = name;
		boolean unique = false;
		int counter = 0;
		while(! unique) {
			boolean found = false;
			for(MorphConfig config: configs) {
				if(newName.equals(config.getName())) {
					found = true;
					break;
				}
			}
			if(found) 
				newName = name + " " + ++counter;
			else
				unique = true;
		}
		return newName;
	}

	public void addMorphConfig(MorphConfig config) {
		System.out.println("addMorphConfig " + config.getName());
		config.setName(uniquify(config.getName()));
		configs.add(config);
		config.addDefaultMorphView();
		addTab(config.getName(), 
				config.getIcon(), 
				config.getMorphViewsTabbedPane(),
				config.getToolTip());
		setTabComponentAt(this.getTabCount() - 1, 
				new WatchmakerTabComponent(this, config.getIcon(), config.getName()));
		
		setSelectedIndex(this.getTabCount() - 1);
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
