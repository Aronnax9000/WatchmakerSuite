package net.richarddawkins.watchmaker.gui;

import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.richarddawkins.watchmaker.morph.MorphConfig;

public class MorphViewsTabbedPane extends JTabbedPane {

	protected MorphConfig config;

	private static final long serialVersionUID = -9105080336982806166L;

	public MorphViewsTabbedPane(MorphConfig config) {
		this.config = config;
		addChangeListener(new TabChangeListener());
	}
	
	protected Vector<MorphView> morphViews = new Vector<MorphView>();

	public String uniquify(String name) {
		String newName = name;
		boolean unique = false;
		int counter = 0;
		while(! unique) {
			boolean found = false;
			for(MorphView morphView: morphViews) {
				if(newName.equals(morphView.getName())) {
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
	
	public void addMorphView(MorphView view) {
		System.out.println("addMorphView " + view.getName());
		view.setName(uniquify(view.getName()));
		morphViews.add(view);

		addTab(view.getName(),
				null, 
				view,
				view.getToolTip());
		setTabComponentAt(this.getTabCount() - 1, 
				new MorphViewTabComponent(this, view.getIcon(), view.getName()));
		setSelectedIndex(this.getTabCount() - 1);
	}
	
    class TabChangeListener implements ChangeListener {
	    public void stateChanged(ChangeEvent e) {
	    	@SuppressWarnings("unused")
			MorphView morphView = morphViews.get(getSelectedIndex());
	    	// Do nothing (here's where the morphView could have a crack at changing the menu bar)
	    }
   }

	public MorphConfig getMorphConfig() {
		return config;
	}
	
	
}
