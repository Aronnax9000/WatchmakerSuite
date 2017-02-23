package net.richarddawkins.watchmaker.swing.menu;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import net.richarddawkins.watchmaker.app.AppData;
import net.richarddawkins.watchmaker.menu.WatchmakerAction;

abstract public class SwingWatchmakerAction extends AbstractAction implements WatchmakerAction {

private static final long serialVersionUID = 1L;

	protected AppData appData;

    public SwingWatchmakerAction(AppData appData, String name) {
        super(name);
        this.appData = appData;
    }
 
    public SwingWatchmakerAction(AppData appData, String name, Icon icon) {
        super(name, icon);
        this.appData = appData;
    }
 
    public SwingWatchmakerAction(AppData appData, String name, Integer mnemonic) {
        this(appData, name);
        putValue(MNEMONIC_KEY, mnemonic);
    }

    public SwingWatchmakerAction(AppData appData, String name, Icon icon, KeyStroke accelerator) {
        this(appData, name, icon);
        putValue(Action.ACCELERATOR_KEY, accelerator);
    }


}
