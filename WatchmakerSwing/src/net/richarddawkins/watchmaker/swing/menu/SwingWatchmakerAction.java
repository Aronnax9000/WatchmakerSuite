package net.richarddawkins.watchmaker.swing.menu;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.image.ClassicImageLoader;
import net.richarddawkins.watchmaker.image.ClassicImageLoaderService;
import net.richarddawkins.watchmaker.menu.WatchmakerAction;
import net.richarddawkins.watchmaker.swing.zoo.SwingMultiMorphTypeTabbedPanel;

abstract public class SwingWatchmakerAction extends AbstractAction implements WatchmakerAction {

private static final long serialVersionUID = 1L;

	public AppData getAppData() {
	    return SwingMultiMorphTypeTabbedPanel.getInstance().getSelectedAppData();
	}
	
	public ClassicImageLoader getClassicImageLoader() {
	    return ClassicImageLoaderService.getInstance().getClassicImageLoader();
	}
	
    public SwingWatchmakerAction(String name) {
        super(name);
    }
    
    public SwingWatchmakerAction(String name, Icon icon) {
        super(name, icon);
    }
 
    public SwingWatchmakerAction(String name, Integer mnemonic) {
        this(name);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public SwingWatchmakerAction(String name, Icon icon, KeyStroke accelerator) {
        this(name, icon);
        putValue(Action.ACCELERATOR_KEY, accelerator);
    }

    

}
